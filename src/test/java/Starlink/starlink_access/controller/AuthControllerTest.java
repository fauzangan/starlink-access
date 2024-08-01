package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.AuthDTO;
import Starlink.starlink_access.DTO.AuthResponse;
import Starlink.starlink_access.exception.GlobalExceptionHandler;
import Starlink.starlink_access.exception.InvalidCredentialsException;
import Starlink.starlink_access.exception.InvalidTokenException;
import Starlink.starlink_access.exception.UserAlreadyExistsException;
import Starlink.starlink_access.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Pastikan ini ada
                .build();
    }

    // Login Tests

    @Test
    public void testLogin_Success() throws Exception {
        AuthDTO.LoginRequest loginRequest = new AuthDTO.LoginRequest("testUser", "Test@1234");
        AuthResponse authResponse = new AuthResponse("accessToken", "refreshToken");

        when(authService.login(any(AuthDTO.LoginRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\", \"password\":\"Test@1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));

        verify(authService, times(1)).login(any(AuthDTO.LoginRequest.class));
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        when(authService.login(any(AuthDTO.LoginRequest.class)))
                .thenThrow(new InvalidCredentialsException("Invalid username or password"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\", \"password\":\"wrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> {
            System.out.println("Response: " + result.getResponse().getContentAsString());
        });;

        verify(authService, times(1)).login(any(AuthDTO.LoginRequest.class));
    }

    @Test
    public void testLogin_EmptyUsername() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"\", \"password\":\"Test@1234\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_EmptyPassword() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\", \"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_MissingUsername() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"password\":\"Test@1234\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_MissingPassword() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\"}"))
                .andExpect(status().isBadRequest());
    }

    // Register Tests

    @Test
    public void testRegister_Success() throws Exception {
        AuthDTO.RegisterRequest registerRequest = new AuthDTO.RegisterRequest("newUser", "StrongPass1@", "newUser@example.com");
        AuthResponse authResponse = new AuthResponse("accessToken", "refreshToken");

        when(authService.register(any(AuthDTO.RegisterRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"newUser\", \"password\":\"StrongPass1@\", \"email\":\"newUser@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));

        verify(authService, times(1)).register(any(AuthDTO.RegisterRequest.class));
    }

    @Test
    public void testRegister_UserAlreadyExists() throws Exception {
        when(authService.register(any(AuthDTO.RegisterRequest.class)))
                .thenThrow(new UserAlreadyExistsException("Username is already taken"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"existingUser\", \"password\":\"StrongPass1@\", \"email\":\"existingUser@example.com\"}"))
                .andExpect(status().isBadRequest());

        verify(authService, times(1)).register(any(AuthDTO.RegisterRequest.class));
    }

    @Test
    public void testRegister_EmptyUsername() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"\", \"password\":\"StrongPass1@\", \"email\":\"newUser@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_EmptyPassword() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"newUser\", \"password\":\"\", \"email\":\"newUser@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_EmptyEmail() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"newUser\", \"password\":\"StrongPass1@\", \"email\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_MissingUsername() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"password\":\"StrongPass1@\", \"email\":\"newUser@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_MissingPassword() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"newUser\", \"email\":\"newUser@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_MissingEmail() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"newUser\", \"password\":\"StrongPass1@\"}"))
                .andExpect(status().isBadRequest());
    }

    // Refresh Token Tests

    @Test
    public void testRefreshToken_Success() throws Exception {
        AuthResponse authResponse = new AuthResponse("newAccessToken", "validRefreshToken");

        when(authService.refreshToken(anyString())).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/refresh")
                        .header("Authorization", "Bearer validRefreshToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value("validRefreshToken"));

        verify(authService, times(1)).refreshToken("Bearer validRefreshToken");
    }

    @Test
    public void testRefreshToken_InvalidToken() throws Exception {
        when(authService.refreshToken(anyString()))
                .thenThrow(new InvalidTokenException("Invalid refresh token"));

        mockMvc.perform(post("/api/auth/refresh")
                        .header("Authorization", "Bearer invalidRefreshToken"))
                .andExpect(status().isUnauthorized());

        verify(authService, times(1)).refreshToken("Bearer invalidRefreshToken");
    }

    @Test
    public void testRefreshToken_MissingToken() throws Exception {
        mockMvc.perform(post("/api/auth/refresh"))
                .andExpect(status().isBadRequest());
    }
}
