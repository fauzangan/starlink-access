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
                .birthDate(user.getBirthDate())
                .build();
    }

    public static User map(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .birthDate(userDTO.getBirthDate())
                .build();
    }
}
