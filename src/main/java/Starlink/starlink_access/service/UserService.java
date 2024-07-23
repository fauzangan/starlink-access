package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.UserDTO;
import Starlink.starlink_access.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDTO create(UserDTO productDTO);
    Page<UserDTO> getAll(Pageable pageable, UserDTO productDTO);
    UserDTO update(Long id, UserDTO productDTO);
    void delete (Long id);
}

