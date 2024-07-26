package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.model.Transaction;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {
    private Long id;
    private Long price;
    private Long quantity;
    private Long product_id;
    private Long transaction_id;
}