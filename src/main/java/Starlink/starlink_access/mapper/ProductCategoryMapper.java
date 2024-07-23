package Starlink.starlink_access.mapper;

import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.model.ProductCategory;

public class ProductCategoryMapper {
    public static ProductCategoryDTO map(ProductCategory productCategory) {
        return ProductCategoryDTO.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .build();
    }

    public static ProductCategory map(ProductCategoryDTO productCategoryDTO) {
        return ProductCategory.builder()
                .id(productCategoryDTO.getId())
                .name(productCategoryDTO.getName())
                .build();
    }
}
