package Starlink.starlink_access.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
    private Long id;

    private String name;

    private Long percentage;
}

