package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Statistic;
import Starlink.starlink_access.validator.Lowercase;
import Starlink.starlink_access.validator.Password;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserDTO {

    private Long id;

    @Lowercase
    private String username;

    @Password
    private String password;

    private String role;
    @Email(message = "Email should be valid")
    private String email;

    private String name;

    private String birthDate;

    private List<Statistic> statistics;
}
