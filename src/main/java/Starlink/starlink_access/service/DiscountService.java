package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.DiscountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscountService {
    DiscountService create(DiscountDTO productDto);
    Page<DiscountService> getAll(Pageable pageable, DiscountDTO productDTO);
    DiscountService update(Long id, DiscountDTO productDto);
    void delete (Long id);
}
