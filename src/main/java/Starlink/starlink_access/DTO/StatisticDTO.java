package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {
    private Long id;

    private User user;

    @Min(value = 1, message = "Quota must be at least 1")
    private Long quota;

    @Min(value = 1, message = "Expired date must be at least 1")
    private Long expiredDate;
}
