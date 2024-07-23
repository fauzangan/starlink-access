package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Discount;
import Starlink.starlink_access.model.Product;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;

    private Long quantity;

    private Long total_price;

    private Long created_at;

    private Long updated_at;

    private Long expired_date;

    private StatisticDTO serviceDetail;

    private Long product;

    private Long discount;
}
