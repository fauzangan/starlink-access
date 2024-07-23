package Starlink.starlink_access.mapper;

import Starlink.starlink_access.DTO.ProductListDTO;
import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.model.ProductList;
import Starlink.starlink_access.model.Transaction;
import Starlink.starlink_access.utils.DateFormatter;

import java.util.stream.Collectors;

public class TransactionMapper {

    public static TransactionDTO map(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .quantity(transaction.getQuantity())
                .total_price(transaction.getTotal_price())
                .created_at(DateFormatter.convertLongToStringDate(transaction.getCreated_at()))
                .updated_at(DateFormatter.convertLongToStringDate(transaction.getUpdated_at()))
                .expired_date(DateFormatter.convertLongToStringDate(transaction.getExpired_date()))
                .is_settled(transaction.getIs_settled())
                .user_id(transaction.getUser().getId())
                .discount(transaction.getDiscount().getPercentage())
                .productLists(transaction.getProductLists().stream()
                        .map(productList -> ProductListDTO.builder()
                                .id(productList.getId())
                                .product_id(productList.getProduct().getId())
                                .price(productList.getPrice())
                                .quantity(productList.getQuantity())
                                .transaction_id(productList.getTransaction().getId())
                                .build()
                        ).collect(Collectors.toList())
                ).build();
    }
//
//    public static Transaction map(TransactionDTO transactionDTO) {
//        return Transaction.builder()
//                .id(transactionDTO.getId())
//                .quantity(transactionDTO.getQuantity())
//                .total_price(transactionDTO.getTotal_price())
//                .created_at(DateFormatter.convertStringDateToLong(transactionDTO.getCreated_at()))
//                .updated_at(DateFormatter.convertStringDateToLong(transactionDTO.getUpdated_at()))
//                .expired_date(DateFormatter.convertStringDateToLong(transactionDTO.getExpired_date()))
//                .is_settled(transactionDTO.getIs_settled())
//                .productLists(transactionDTO.getProducts().stream()
//                        .map().collect(Collectors.toList())
//                )
//                .discount(DiscountMapper.map(transactionDTO.getDiscount()))
//                .build();
//    }
}
