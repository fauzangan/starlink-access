package Starlink.starlink_access.model;

import Starlink.starlink_access.validator.Lowercase;
import Starlink.starlink_access.validator.Password;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lowercase
    private String username;

    @Column(nullable = false)
    @Password
    private String password;

    @Column(nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    private String name;


    @OneToMany(mappedBy = "user")
    private List<Statistic> statistics;


    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER, ADMIN
    }
}
