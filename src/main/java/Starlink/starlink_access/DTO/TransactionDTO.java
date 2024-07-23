package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Discount;
import Starlink.starlink_access.model.Product;


import Starlink.starlink_access.util.dto.ProductListDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private Long quantity;
    private Long total_price;
    private String created_at;
    private String updated_at;
    private String expired_date;
    private Boolean is_settled;
    private Long user_id;
    private Integer discount;
    private List<ProductListDTO> products;
}
