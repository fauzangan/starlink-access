package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.UserDTO;
import Starlink.starlink_access.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User create(UserDTO productDTO);
    Page<User> getAll(Pageable pageable, UserDTO productDTO);
    User update(Long id, UserDTO productDTO);
    void delete (Long id);
}

