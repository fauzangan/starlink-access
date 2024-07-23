package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.User;
import jakarta.persistence.Column;

import java.time.LocalDate;

public class StatisticDTO {
    @Column(nullable = false)
    private Long user_id;

    private UserDTO user;

    @Column(nullable = false)
    private Long quota;

    @Column(nullable = false)
    private LocalDate expiredDate;
}
