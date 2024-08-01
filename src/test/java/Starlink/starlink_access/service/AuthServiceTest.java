package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.AuthDTO;
import Starlink.starlink_access.DTO.AuthResponse;
import Starlink.starlink_access.exception.InvalidCredentialsException;
import Starlink.starlink_access.exception.InvalidTokenException;
import Starlink.starlink_access.exception.UserAlreadyExistsException;
import Starlink.starlink_access.model.User;
import Starlink.starlink_access.repository.UserRepository;
import Starlink.starlink_access.security.JwtUtil;
import Starlink.starlink_access.service.implementation.AuthServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImplementation authService;

    private AuthDTO.LoginRequest loginRequest;
    private AuthDTO.RegisterRequest registerRequest;
    private Authentication authentication;
    private User user;
    private UserDetails userDetails;
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        loginRequest = new AuthDTO.LoginRequest("testUser", "Test@1234");
        registerRequest = new AuthDTO.RegisterRequest("testUser", "Test@1234", "test@example.com");

        user = new User();
        user.setUsername("testUser");
        user.setPassword("Test@1234");
        user.setEmail("test@example.com");
        user.setRole(User.Role.USER);

        userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(User.Role.USER.name())
                .build();

        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testLogin_Success() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateAccessToken(any(UserDetails.class))).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(any(UserDetails.class))).thenReturn("refreshToken");

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void testRegister_Success() {
        when(userRepository.existsByUsername(registerRequest.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUtil.generateAccessToken(any(UserDetails.class))).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(any(UserDetails.class))).thenReturn("refreshToken");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    @Test
    public void testRefreshToken_Success() {
        when(jwtUtil.validateRefreshToken("validRefreshToken")).thenReturn(true);
        when(jwtUtil.extractUsername("validRefreshToken")).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(jwtUtil.generateAccessToken(any(UserDetails.class))).thenReturn("newAccessToken");

        AuthResponse response = authService.refreshToken("validRefreshToken");

        assertNotNull(response);
        assertEquals("newAccessToken", response.getAccessToken());
        assertEquals("validRefreshToken", response.getRefreshToken());
    }

    @Test
    public void testGetUserAuthenticated_Success() {
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        User authenticatedUser = authService.getUserAuthenticated();

        assertNotNull(authenticatedUser);
        assertEquals("testUser", authenticatedUser.getUsername());
    }

    @Test
    public void testLogin_InvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new InvalidCredentialsException("Invalid username or password"));

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            authService.login(loginRequest);
        });

        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    public void testRegister_UsernameAlreadyExists() {
        when(userRepository.existsByUsername(registerRequest.getUsername())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            authService.register(registerRequest);
        });

        assertEquals("Username is already taken", exception.getMessage());
    }

    @Test
    public void testRegister_InvalidPassword() {
        registerRequest.setPassword("weak");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(registerRequest);
        });

        assertTrue(exception.getMessage().contains("Password does not meet strength requirements"));
    }

    @Test
    public void testRefreshToken_InvalidToken() {
        when(jwtUtil.validateRefreshToken("invalidRefreshToken")).thenReturn(false);

        InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
            authService.refreshToken("invalidRefreshToken");
        });

        assertEquals("Invalid refresh token", exception.getMessage());
    }

    @Test
    public void testGetUserAuthenticated_UserNotFound() {
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.getUserAuthenticated();
        });

        assertEquals("Unauthorize, user not found", exception.getMessage());
    }
}
