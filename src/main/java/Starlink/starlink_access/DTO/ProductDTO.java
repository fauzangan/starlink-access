package Starlink.starlink_access.DTO;


import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private ProductCategoryDTO productCategory;
    private String name;
    private Long price;
    private Long stock;
    private String picture_source;
}
