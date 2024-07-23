package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.UserDTO;
import Starlink.starlink_access.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface UserService {
    UserDTO getUserProfile(Long userId);
    UserDTO updateUserProfile(Long userId, UserDTO userDto);
    Page<UserDTO> getAllUsers(Pageable pageable);
    UserDTO getUserById(Long userId);
    Long getUserIdByUsername(String username);
}

