package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Statistic;
import Starlink.starlink_access.validator.Lowercase;
import Starlink.starlink_access.validator.Password;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;

import java.util.List;

public class UserDTO {

    private Long id;

    @Column(nullable = false)
    @Lowercase
    private String username;

    @Column(nullable = false)
    @Password
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birthDate;

    private List<Statistic> statistics;
}
