package Starlink.starlink_access.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailDTO {
    @NotBlank(message = "Order id is required")
    private String order_id;

    @Min(value = 1, message = "Product id must be at least 1")
    private Long gross_amount;
}
