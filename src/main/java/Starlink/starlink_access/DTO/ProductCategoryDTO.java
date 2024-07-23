package Starlink.starlink_access.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryDTO {
    private Long id;

    private String name;
}