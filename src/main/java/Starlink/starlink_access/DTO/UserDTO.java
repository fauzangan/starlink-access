package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Statistic;
import Starlink.starlink_access.model.User;
import Starlink.starlink_access.validator.Lowercase;
import Starlink.starlink_access.validator.Password;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @Lowercase
    private String username;

    @Password
    private String password;

    @Enumerated(EnumType.STRING)
    private User.Role role;

    @Email(message = "Email should be valid")
    private String email;

    private String name;

    private List<Statistic> statistics;
}
