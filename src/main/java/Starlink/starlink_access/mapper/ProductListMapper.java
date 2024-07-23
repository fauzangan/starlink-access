package Starlink.starlink_access.mapper;

import Starlink.starlink_access.DTO.ProductListDTO;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.model.ProductList;

public class ProductListMapper {

    public static ProductList map(ProductListDTO productListDTO) {
        return ProductList.builder()
                .id(productListDTO.getId())
                .price(productListDTO.getPrice())
                .quantity(productListDTO.getQuantity())
                .product(ProductMapper.map(productListDTO.getProduct()))
                .transaction(TransactionMapper.map(productListDTO.getTransaction()))
                .build();
    }

    public static ProductListDTO map(ProductList productList) {
        return ProductListDTO.builder()
                .id(productList.getId())
                .price(productList.getPrice())
                .quantity(productList.getQuantity())
                .product(ProductMapper.map(productList.getProduct()))
                .transaction(TransactionMapper.map(productList.getTransaction()))
                .build();
    }
}
