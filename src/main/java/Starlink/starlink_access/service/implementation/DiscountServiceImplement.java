package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.DTO.DiscountDTO;
import Starlink.starlink_access.service.DiscountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DiscountServiceImplement implements DiscountService {

    @Override
    public DiscountDTO create(DiscountDTO productDto) {
        return null;
    }

    @Override
    public Page<DiscountDTO> getAll(Pageable pageable, DiscountDTO productDTO) {
        return null;
    }

    @Override
    public DiscountDTO update(Long id, DiscountDTO productDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
