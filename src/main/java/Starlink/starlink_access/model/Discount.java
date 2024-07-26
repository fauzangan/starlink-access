package Starlink.starlink_access.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "discounts")
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long percentage;
}
