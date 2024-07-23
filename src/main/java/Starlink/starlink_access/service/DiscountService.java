package Starlink.starlink_access.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscountService {
    DiscountService create(DiscountDto productDto);
    Page<DiscountService> getAll(Pageable pageable, DiscountDto productDto);
    DiscountService update(Long id, DiscountDto productDto);
    void delete (Long id);
}
