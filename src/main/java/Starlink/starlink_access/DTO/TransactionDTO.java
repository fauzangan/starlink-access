package Starlink.starlink_access.DTO;

import Starlink.starlink_access.model.Discount;
import Starlink.starlink_access.model.Product;


public class TransactionDTO {
    private Long id;

    private Long quantity;

    private Long total_price;

    private Long transaction_date;

    private ServiceDetailDTO serviceDetail;

    private Product product;

    private Discount discount;
}
