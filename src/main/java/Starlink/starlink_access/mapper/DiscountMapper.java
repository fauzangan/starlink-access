package Starlink.starlink_access.mapper;

import Starlink.starlink_access.DTO.DiscountDTO;
import Starlink.starlink_access.model.Discount;

public class DiscountMapper {
    public static DiscountDTO map(Discount discount) {
        return DiscountDTO.builder()
                .id(discount.getId())
                .name(discount.getName())
                .percentage(discount.getPercentage())
                .build();
    }

    public static Discount map(DiscountDTO discountDTO) {
        return Discount.builder()
                .id(discountDTO.getId())
                .name(discountDTO.getName())
                .percentage(discountDTO.getPercentage())
                .build();
    }
}
