package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.DiscountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscountService {
    DiscountDTO create(DiscountDTO request);
    Page<DiscountDTO> getAll(Pageable pageable, String name, Integer percentage);
    DiscountDTO getById(Long id);
    DiscountDTO update(Long id, DiscountDTO discountDTO);
    void delete (Long id);
}
