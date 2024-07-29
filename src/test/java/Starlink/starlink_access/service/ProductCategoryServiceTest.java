package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.mapper.ProductCategoryMapper;
import Starlink.starlink_access.model.ProductCategory;
import Starlink.starlink_access.repository.ProductCategoryRepository;
import Starlink.starlink_access.service.implementation.ProductCategoryImplement;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class ProductCategoryServiceTest {

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductCategoryImplement productCategoryImplement;

    private ProductCategoryDTO productCategoryDTO;
    private ProductCategory productCategory;

    @BeforeEach
    void setup() {
        productCategoryDTO = ProductCategoryDTO.builder()
                .id(1L)
                .name("Smart antenna")
                .build();

        productCategory = ProductCategory.builder()
                .id(productCategoryDTO.getId())
                .name(productCategoryDTO.getName())
                .build();
    }

    @DisplayName("JUnit test for Create Product Category")
    @Test
    public void givenProductCategoryDTO_whenSave_thenSuccesSave() {
        given(productCategoryRepository.save(any(ProductCategory.class))).willReturn(productCategory);

        ProductCategory savedCategory = productCategoryImplement.create(productCategoryDTO.getName());

        assertNotNull(savedCategory);
        assertEquals(productCategoryDTO.getName(), savedCategory.getName());

        verify(productCategoryRepository, times(1)).save(any(ProductCategory.class));
    }

    @DisplayName("JUnit test for Get All Product Categories")
    @Test
    public void getAllProductCategories_success() {
        List<ProductCategory> categories = new ArrayList<>();
        categories.add(productCategory);
        categories.add(ProductCategory.builder().id(2L).name("Satellite Dish").build());

        given(productCategoryRepository.findAll()).willReturn(categories);

        List<ProductCategory> retrievedCategories = productCategoryImplement.getAll();

        assertNotNull(retrievedCategories);
        assertEquals(2, retrievedCategories.size());

        verify(productCategoryRepository, times(1)).findAll();
    }

    @DisplayName("JUnit test for Get the Product Category")
    @Test
    public void givenProductCategoryId_whenGetOne_thenSuccess() {
        given(productCategoryRepository.findById(1L)).willReturn(Optional.of(productCategory));

        ProductCategory retrievedCategory = productCategoryImplement.getOne(1L);

        assertNotNull(retrievedCategory);
        assertEquals(productCategoryDTO.getName(), retrievedCategory.getName());

        verify(productCategoryRepository, times(1)).findById(1L);
    }

    @DisplayName("JUnit test for Get the Product Category - Exception")
    @Test
    public void givenInvalidProductCategoryId_whenGetOne_thenThrowsException() {
        given(productCategoryRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productCategoryImplement.getOne(1L);
        });

        verify(productCategoryRepository, times(1)).findById(1L);
    }

    @DisplayName("JUnit test for Update Product Category")
    @Test
    public void givenProductCategoryIdAndDTO_whenUpdate_thenSuccess() {
        given(productCategoryRepository.findById(1L)).willReturn(Optional.of(productCategory));
        given(productCategoryRepository.save(any(ProductCategory.class))).willReturn(productCategory);

        ProductCategory updatedCategory = productCategoryImplement.update(1L, "Updated Name");

        assertNotNull(updatedCategory);
        assertEquals("Updated Name", updatedCategory.getName());

        verify(productCategoryRepository, times(1)).save(any(ProductCategory.class));
    }

    @DisplayName("JUnit test for Delete Product Category")
    @Test
    public void givenProductCategoryId_whenDelete_thenSuccess() {
        given(productCategoryRepository.findById(1L)).willReturn(Optional.of(productCategory));
        willDoNothing().given(productCategoryRepository).delete(any(ProductCategory.class));

        productCategoryImplement.delete(1L);

        verify(productCategoryRepository, times(1)).delete(any(ProductCategory.class));
    }
    @DisplayName("JUnit test for Create Product Category - Failure")
    @Test
    public void givenProductCategoryDTO_whenSave_thenThrowException() {
        given(productCategoryRepository.save(any(ProductCategory.class))).willThrow(new RuntimeException("Failed to save category"));

        assertThrows(RuntimeException.class, () -> {
            productCategoryImplement.create(productCategoryDTO.getName());
        });

        verify(productCategoryRepository, times(1)).save(any(ProductCategory.class));
    }

    @DisplayName("JUnit test for Get Product Category - Not Found")
    @Test
    public void givenInvalidProductCategoryId_whenGetOne_thenThrowException() {
        given(productCategoryRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productCategoryImplement.getOne(1L);
        });

        verify(productCategoryRepository, times(1)).findById(1L);
    }

    @DisplayName("JUnit test for Update Product Category - Not Found")
    @Test
    public void givenInvalidProductCategoryId_whenUpdate_thenThrowException() {
        given(productCategoryRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productCategoryImplement.update(1L, "Updated Name");
        });

        verify(productCategoryRepository, times(1)).findById(1L);
    }

    @DisplayName("JUnit test for Delete Product Category - Not Found")
    @Test
    public void givenInvalidProductCategoryId_whenDelete_thenThrowException() {
        given(productCategoryRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productCategoryImplement.delete(1L);
        });

        verify(productCategoryRepository, times(1)).findById(1L);
    }

    @DisplayName("JUnit test for Get Product Category - Runtime Exception")
    @Test
    public void givenProductCategoryId_whenGetOne_thenRuntimeException() {
        given(productCategoryRepository.findById(1L)).willThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            productCategoryImplement.getOne(1L);
        });

        verify(productCategoryRepository, times(1)).findById(1L);
    }
}
