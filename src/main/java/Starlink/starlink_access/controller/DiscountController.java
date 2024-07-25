package Starlink.starlink_access.controller;


import Starlink.starlink_access.DTO.DiscountDTO;
import Starlink.starlink_access.model.Discount;
import Starlink.starlink_access.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/admin/discounts")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DiscountDTO request){
        DiscountDTO createdDiscount = discountService.create(request);
        return ResponseEntity.ok(createdDiscount);
    }

    @GetMapping
    public ResponseEntity<Page<DiscountDTO>> getAll(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam (required = false) String name,
            @RequestParam (required = false) Integer percentage){
        Page<DiscountDTO> discounts = discountService.getAll(pageable, name, percentage);
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountDTO> getById(@PathVariable Long id){
        DiscountDTO discount = discountService.getById(id);
        return ResponseEntity.ok(discount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountDTO> update(@PathVariable Long id, @Valid @RequestBody DiscountDTO request){
        DiscountDTO updatedDiscount = discountService.update(id, request);
        return ResponseEntity.ok(updatedDiscount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        discountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
