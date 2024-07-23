package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.DiscountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscountService {
    DiscountDTO create(DiscountDTO productDto);
    Page<DiscountDTO> getAll(Pageable pageable, DiscountDTO productDTO);
    DiscountDTO update(Long id, DiscountDTO productDto);
    void delete (Long id);
}
