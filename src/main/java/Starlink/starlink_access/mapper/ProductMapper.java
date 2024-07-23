package Starlink.starlink_access.mapper;

import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.model.Product;

public class ProductMapper {
    public static ProductDTO map(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .productCategory(ProductCategoryMapper.map(product.getProductCategory()))
                .build();
    }

    public static Product map(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .productCategory(ProductCategoryMapper.map(productDTO.getProductCategory()))
                .build();
    }
}
