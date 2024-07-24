package Starlink.starlink_access.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnlyForProductDTO {
    private Long productCategory_id;
    private String name;
    private Long price;
    private Long stock;
    private String picture_source;
}
