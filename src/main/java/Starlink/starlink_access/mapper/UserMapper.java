package Starlink.starlink_access.mapper;

import Starlink.starlink_access.DTO.UserDTO;
import Starlink.starlink_access.model.User;

public class UserMapper {

    public static UserDTO map(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static User map(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(User.Role.valueOf(userDTO.getRole().name()))
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .build();
    }
}
