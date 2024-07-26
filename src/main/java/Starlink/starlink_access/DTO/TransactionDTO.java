package Starlink.starlink_access.DTO;

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

    private String transaction_status;

    private Long user_id;

    private List<ProductListDTO> productLists;

    private Long discount;

    private String payment_type;

    private TransactionDetailDTO transaction_details;

    private BankTransferDTO bank_transfer;

    private String virtualNumber;
}
