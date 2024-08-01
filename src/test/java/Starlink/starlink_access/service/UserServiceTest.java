package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.UserDTO;
import Starlink.starlink_access.exception.ResourceNotFoundException;
import Starlink.starlink_access.mapper.UserMapper;
import Starlink.starlink_access.model.User;
import Starlink.starlink_access.repository.UserRepository;
import Starlink.starlink_access.service.implementation.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImplementation userService;

    @Test
    public void testGetUserIdByUsername_UserFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Long userId = userService.getUserIdByUsername("testuser");

        assertNotNull(userId);
        assertEquals(1L, userId);
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testGetUserIdByUsername_UserNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserIdByUsername("testuser");
        });

        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testGetAllUsers_UsersFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Page<User> users = new PageImpl<>(Collections.singletonList(user));
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findAll(pageable)).thenReturn(users);

        Page<UserDTO> result = userService.getAllUsers(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetAllUsers_NoUsersFound() {
        Page<User> users = new PageImpl<>(Collections.emptyList());
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findAll(pageable)).thenReturn(users);

        Page<UserDTO> result = userService.getAllUsers(pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetUserById_UserFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserProfile_UserFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserProfile(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserProfile_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserProfile(1L);
        });

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateUserProfile_UserFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("oldUsername");
        user.setEmail("oldEmail@example.com");

        UserDTO userDto = new UserDTO();
        userDto.setUsername("newUsername");
        userDto.setEmail("newEmail@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.updateUserProfile(1L, userDto);

        assertNotNull(result);
        assertEquals("newUsername", result.getUsername());
        assertEquals("newEmail@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUserProfile_UserNotFound() {
        UserDTO userDto = new UserDTO();
        userDto.setUsername("newUsername");
        userDto.setEmail("newEmail@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.updateUserProfile(1L, userDto);
        });

        verify(userRepository, times(1)).findById(1L);
    }
}
