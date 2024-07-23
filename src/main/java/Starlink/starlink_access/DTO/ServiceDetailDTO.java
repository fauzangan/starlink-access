package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.User;
import jakarta.persistence.Column;

import java.time.LocalDate;

public class ServiceDetailDTO {
    private Long user_id;

    private UserDTO user;

    private Long quota;

    private LocalDate expiredDate;
}
