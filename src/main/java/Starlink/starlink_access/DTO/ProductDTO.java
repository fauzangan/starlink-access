package Starlink.starlink_access.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private Long id;

    private ProductCategoryDTO productCategory;

    private String name;


    private Long price;


    private Long stock;
    private String picture_source;
}
