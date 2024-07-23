package Starlink.starlink_access.mapper;

import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.model.Transaction;

public class TransactionMapper {

    public static TransactionDTO map(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .quantity(transaction.getQuantity())
                .totalPrice(transaction.getTotal_price())
                .transactionDate(transaction.getTransaction_date())
                .expiredDate(transaction.getExpired_date())
                .isSettled(transaction.getIs_settled())
                .serviceDetail(StatisticMapper.map(transaction.getServiceDetail()))
                .discount(DiscountMapper.map(transaction.getDiscount()))
                .build();
    }

    public static Transaction map(TransactionDTO transactionDTO) {
        return Transaction.builder()
                .id(transactionDTO.getId())
                .quantity(transactionDTO.getQuantity())
                .total_price(transactionDTO.getTotalPrice())
                .transaction_date(transactionDTO.getTransactionDate())
                .expired_date(transactionDTO.getExpiredDate())
                .is_settled(transactionDTO.getIsSettled())
                .serviceDetail(StatisticMapper.map(transactionDTO.getServiceDetail()))
                .discount(DiscountMapper.map(transactionDTO.getDiscount()))
                .build();
    }
}
