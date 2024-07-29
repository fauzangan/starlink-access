package Starlink.starlink_access.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class ProductCategoryDTO {

    private Long id;

    private String name;
}