package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.model.Transaction;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductListDTO {
    private Long id;

    private Integer price;

    private Integer quantity;

    private ProductDTO product;

    private Transaction transaction;
}