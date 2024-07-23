package Starlink.starlink_access.service.implementation;

import Starlink.starlink_access.DTO.DiscountDTO;
import Starlink.starlink_access.mapper.DiscountMapper;
import Starlink.starlink_access.model.Discount;
import Starlink.starlink_access.model.Transaction;
import Starlink.starlink_access.repository.DiscountRepository;
import Starlink.starlink_access.service.DiscountService;
import Starlink.starlink_access.util.specification.DiscountSpesification;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImplementation implements DiscountService {
    private final DiscountRepository discountRepository;

    @Override
    public DiscountDTO create(DiscountDTO discountDTO) {
        Discount discount = DiscountMapper.map(discountDTO);
        Discount saved = discountRepository.save(discount);
        return DiscountMapper.map(saved);
    }

    @Override
    public Page<DiscountDTO> getAll(Pageable pageable, String name, Integer percentage) {
        var specification = DiscountSpesification.discountSpecification(name, percentage);
        Page<Discount> discounts = discountRepository.findAll(specification, pageable);
        return discounts.map(DiscountMapper::map);
    }

    @Override
    public DiscountDTO getById(Long id){
        Discount discount = discountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Discount Not Found"));
        return DiscountMapper.map(discount);
    }

    @Override
    public DiscountDTO update(Long id, DiscountDTO discountDTO) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Discount Not Found"));

        existingDiscount.setName(discountDTO.getName());
        existingDiscount.setPercentage(discountDTO.getPercentage());
        Discount updatedDiscount = discountRepository.save(existingDiscount);

        return DiscountMapper.map(updatedDiscount);
    }

    @Override
    public void delete(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Discount  Not Found"));
        discountRepository.delete(discount);
    }
}
