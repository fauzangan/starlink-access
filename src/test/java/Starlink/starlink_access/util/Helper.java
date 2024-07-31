package Starlink.starlink_access.util;

import Starlink.starlink_access.DTO.BankTransferDTO;
import Starlink.starlink_access.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static User createUser() {
        return User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();
    }

    public static ProductCategory createProductCategory() {
        return ProductCategory.builder()
                .id(1L)
                .name("Test Category")
                .build();
    }

    public static Product createProduct() {
        return Product.builder()
                .id(1L)
                .name("Test Product")
                .price(100000L)
                .stock(10L)
                .productCategory(createProductCategory())
                .picture_source("http://example.com/image.jpg")
                .build();
    }

    public static Discount createDiscount() {
        return Discount.builder()
                .id(1L)
                .name("Test Discount")
                .percentage(10L)
                .build();
    }

    public static Transaction createTransaction() {
        return Transaction.builder()
                .id(1L)
                .user(createUser())
                .discount(createDiscount())
                .expired_date(LocalDateTime.now().plusDays(1).toEpochSecond(java.time.ZoneOffset.UTC))
                .total_price(90000L)
                .quantity(1L)
                .transaction_status("pending")
                .productLists(new ArrayList<>())
                .build();
    }

    public static ProductList createProductList() {
        return ProductList.builder()
                .id(1L)
                .product(createProduct())
                .transaction(createTransaction())
                .price(90000L)
                .quantity(1L)
                .build();
    }

    public static BankTransferDTO createBankTransferDTO(){
        return BankTransferDTO.builder()
                .bank("bca")
                .build();
    }
}