package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.UserDTO;
import Starlink.starlink_access.model.User;
import Starlink.starlink_access.repository.UserRepository;
import Starlink.starlink_access.service.UserService;
import Starlink.starlink_access.service.implementation.UserServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserServiceImplementation userService;

    @Mock
    private Authentication authentication;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserProfile_Success() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");

        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userService.getUserProfile(1L)).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/profile")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(userDTO)));

        verify(userService, times(1)).getUserProfile(1L);
    }

    @Test
    public void testGetUserProfile_UserNotFound() throws Exception {
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/profile")
                        .principal(authentication))
                .andExpect(status().isNotFound());

        verify(userService, never()).getUserProfile(anyLong());
    }

    @Test
    public void testUpdateUserProfile_Success() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("testuser@example.com");

        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userService.updateUserProfile(eq(1L), any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/profile")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(userDTO)));

        verify(userService, times(1)).updateUserProfile(eq(1L), any(UserDTO.class));
    }

    @Test
    public void testUpdateUserProfile_UserNotFound() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("testuser@example.com");

        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/users/profile")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isNotFound());

        verify(userService, never()).updateUserProfile(anyLong(), any(UserDTO.class));
    }
}
