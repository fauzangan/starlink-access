package Starlink.starlink_access.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;

    @NotBlank(message = "Quantity is required")
    private Long quantity;

    @Min(value = 0, message = "Total price must be at least 0")
    private Long total_price;

    private String created_at;

    private String updated_at;

    @NotBlank(message = "Expired date is required")
    private String expired_date;

    @NotBlank(message = "Transaction status is required")
    private String transaction_status;

    @Min(value = 1, message = "User id must be at least 1")
    private Long user_id;

    @NotBlank(message = "Product list is required")
    private List<ProductListDTO> productLists;

    private Long discount;

    @NotBlank(message = "Payment type is required")
    private String payment_type;

    @NotBlank(message = "Transaction details is required")
    private TransactionDetailDTO transaction_details;

    @NotBlank(message = "Bank transfer is required")
    private BankTransferDTO bank_transfer;

    @NotBlank(message = "Virtual number is required")
    private String virtualNumber;
}
