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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    private DiscountDTO updatedDTO;
    private Discount updatedDiscount;

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

        updatedDTO = DiscountDTO.builder()
                .id(1L)
                .name("Updated Cashback")
                .percentage(30L)
                .build();

        updatedDiscount = Discount.builder()
                .id(1L)
                .name("Updated Cashback")
                .percentage(30L)
                .build();
    }

    @Test
    @DisplayName("Test for Create Discount")
    public void givenDiscountDTO_whenSave_Success() {
        given(discountRepository.save(any(Discount.class))).willReturn(discount);

        DiscountDTO disounts = discountService.create(discountDTO);

        assertThat(disounts).isNotNull();
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

    @Test
    @DisplayName("Test for Get One Discount")
    public void givenDiscount_whenGetOne_Success() {
        given(discountRepository.findById(any())).willReturn(Optional.of(discount));

        DiscountDTO discounts = discountService.getById(1L);

        assertThat(discounts).isNotNull();
        assertThat(discounts.getId()).isEqualTo(discountDTO.getId());
        verify(discountRepository).findById(1L);
    }

    @Test
    @DisplayName("Test for update discount")
    public void givenDiscountDTO_whenUpdate_Success() {
        given(discountRepository.findById(any(Long.class))).willReturn(Optional.of(discount));
        given(discountRepository.save(any(Discount.class))).willReturn(updatedDiscount);

        DiscountDTO discounts = discountService.update(1L, updatedDTO);

        assertThat(discounts).isNotNull();
        assertThat(discounts.getName()).isEqualTo(updatedDTO.getName());
        assertThat(discounts.getPercentage()).isEqualTo(updatedDTO.getPercentage());
        verify(discountRepository).findById(1L);
        verify(discountRepository).save(any(Discount.class));
    }

    @Test
    @DisplayName("Test for Delete Discount")
    public void givenDiscountId_WhenDelete_Success(){
        Discount discount = mock(Discount.class);
        when(discountRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(discount));
        assertAll(() -> discountService.delete(1L));
    }
}