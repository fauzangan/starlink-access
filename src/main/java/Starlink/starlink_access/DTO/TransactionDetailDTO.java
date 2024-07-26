package Starlink.starlink_access.DTO;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailDTO {
    private String order_id;
    private Long gross_amount;
}
