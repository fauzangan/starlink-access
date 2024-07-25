package Starlink.starlink_access.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO {
    private Long id;

    private String name;
}