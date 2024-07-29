package Starlink.starlink_access.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Min(value = 0, message = "Price must be at least 0")
    private Long price;

    @Min(value = 1, message = "Stock must be at least 1")
    private Long stock;
    private String picture_source;
}
