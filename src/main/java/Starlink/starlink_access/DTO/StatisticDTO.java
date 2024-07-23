package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {
    private Long id;

    private User user;

    private Long quota;

    private Long expiredDate;
}
