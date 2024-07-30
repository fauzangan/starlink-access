package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.DiscountDTO;
import Starlink.starlink_access.model.Discount;
import Starlink.starlink_access.service.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

public class DiscountControllerTest {

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private DiscountController discountController;

    private DiscountDTO discountDTO;
    private DiscountDTO updatedDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        discountDTO = DiscountDTO.builder()
                .id(1L)
                .name("Cashback")
                .percentage(50L)
                .build();

        updatedDTO = DiscountDTO.builder()
                .id(1L)
                .name("Update Cashback")
                .percentage(50L)
                .build();
    }

    @Test
    @DisplayName("Test for Create Discount")
    public void givenDiscountDTO_whenCreate_thenReturnDiscountDTO() {
        when(discountService.create(any(DiscountDTO.class))).thenReturn(discountDTO);

        ResponseEntity<?> response = discountController.create(discountDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(discountDTO, response.getBody());
        verify(discountService, times(1)).create(any(DiscountDTO.class));
    }

    @Test
    @DisplayName("Test for Get All Discount")
    public void givenDiscountAll_whenGetAll_thenReturnDiscountDTOPage(){
        Page<DiscountDTO> discountPage = new PageImpl<>(List.of(discountDTO));

        given(discountService.getAll(any(), any(), any())).willReturn(discountPage);

        ResponseEntity<Page<DiscountDTO>> response = discountController.getAll(PageRequest.of(0, 10), null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(discountPage, response.getBody());
        verify(discountService).getAll(any(), any(), any());
    }

    @Test
    @DisplayName("Test for Get Discount By Id")
    public void givenDiscountId_whenGetById_thenReturnDiscountDTO(){
        given(discountService.getById(anyLong())).willReturn(discountDTO);

        ResponseEntity<DiscountDTO> response = discountController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(discountDTO, response.getBody());
        verify(discountService).getById(1L);
    }

    @Test
    @DisplayName("Test for Update Discount")
    public void givenDiscountDTO_whenUpdate_thenReturnUpdatedDiscountDTO() {
        given(discountService.update(anyLong(), any(DiscountDTO.class))).willReturn(updatedDTO);

        ResponseEntity<DiscountDTO> response = discountController.update(1L, updatedDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDTO, response.getBody());
        verify(discountService).update(anyLong(), any(DiscountDTO.class));
    }

    @Test
    @DisplayName("Test for Delete Discount")
    public void givenDiscountId_whenDelete_thenReturnNoContent() {
        doNothing().when(discountService).delete(anyLong());

        ResponseEntity<Void> response = discountController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(discountService).delete(anyLong());
    }
}
