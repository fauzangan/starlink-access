package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.model.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Price is required")
    private Long price;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;

    @Min(value = 1, message = "Product id must be at least 1")
    private Long product_id;

    @Min(value = 1, message = "Transaction id must be at least 1")
    private Long transaction_id;
}