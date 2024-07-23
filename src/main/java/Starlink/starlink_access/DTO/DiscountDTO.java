package Starlink.starlink_access.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DiscountDTO {
    private Long id;

    private String name;

    private Integer percentage;
}

