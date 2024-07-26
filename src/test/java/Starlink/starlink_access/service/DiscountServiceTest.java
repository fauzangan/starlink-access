package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.DiscountDTO;
import Starlink.starlink_access.model.Discount;
import Starlink.starlink_access.repository.DiscountRepository;
import Starlink.starlink_access.service.implementation.DiscountServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class DiscountServiceTest {
    @Mock
    private DiscountRepository discountRepository;

    @InjectMocks
    private DiscountServiceImplementation discountService;

    private DiscountDTO discountDTO;
    private Discount discount;

    @BeforeEach
    public void setUp() {
        discountDTO = DiscountDTO.builder()
                .name("Cashback")
                .id(1L)
                .percentage(50L)
                .build();

        discount = Discount.builder()
                .id(1L)
                .name("Cashback")
                .percentage(50L)
                .build();
    }

    @Test
    @DisplayName("Test for Create Discount")
    public void givenDiscountDTO_whenSave_Success() {
        given(discountRepository.save(any(Discount.class))).willReturn(discount);

        DiscountDTO savedDiscount = discountService.create(discountDTO);

        assertThat(savedDiscount).isNotNull();
        verify(discountRepository).save(any(Discount.class));
    }

    @Test
    @DisplayName("Test for Get All Discount")
    public void givenDiscount_whenGetAll_Success() {
        var pageable = PageRequest.of(0, 10);
        Page<Discount> page = new PageImpl<>(List.of(discount));

        given(discountRepository.findAll(any(Specification.class), eq(pageable))).willReturn(page);

        Page<DiscountDTO> discounts = discountService.getAll(pageable, null, null);

        assertThat(discounts).isNotEmpty().hasSize(1);
        verify(discountRepository).findAll(any(Specification.class), eq(pageable));
    }
}