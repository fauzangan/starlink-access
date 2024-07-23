package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Discount;
import Starlink.starlink_access.model.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionDTO {
    private Long id;

    private Long quantity;

    private Long total_price;

    private Long transaction_date;

    private StatisticDTO serviceDetail;

    private Product product;

    private Discount discount;
}
