package Starlink.starlink_access.util.request;

import Starlink.starlink_access.DTO.BankTransferDTO;
import Starlink.starlink_access.DTO.TransactionDetailDTO;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidtransRequest {
    private String payment_type;
    private TransactionDetailDTO transaction_details;
    private BankTransferDTO bank_transfer;
}
