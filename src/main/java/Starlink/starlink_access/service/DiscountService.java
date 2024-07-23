package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.DiscountDTO;
import Starlink.starlink_access.model.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscountService {
    DiscountDTO create(DiscountDTO discountDTO);
    Page<DiscountDTO> getAll(Pageable pageable, DiscountDTO discountDTO);
    DiscountDTO update(Long id, DiscountDTO discountDTO);
    void delete (Long id);
}
