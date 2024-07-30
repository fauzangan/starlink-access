package Starlink.starlink_access.controller;

import Starlink.starlink_access.model.ProductCategory;
import Starlink.starlink_access.service.ProductCategorySevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductCategoryControllerTest {

    @Mock
    private ProductCategorySevice productCategorySevice;

    @InjectMocks
    private ProductCategoryController productCategoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_Success() {
        ProductCategory category = new ProductCategory(1L, "Test Category");
        when(productCategorySevice.create("Test Category")).thenReturn(category);

        ResponseEntity<?> response = productCategoryController.create("Test Category");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(productCategorySevice, times(1)).create("Test Category");
    }

    @Test
    void testGetAll_Success() {
        List<ProductCategory> categories = Arrays.asList(
                new ProductCategory(1L, "Category 1"),
                new ProductCategory(2L, "Category 2")
        );
        when(productCategorySevice.getAll()).thenReturn(categories);

        ResponseEntity<?> response = productCategoryController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productCategorySevice, times(1)).getAll();
    }

    @Test
    void testGetOne_Success() {
        ProductCategory category = new ProductCategory(1L, "Test Category");
        when(productCategorySevice.getOne(1L)).thenReturn(category);

        ResponseEntity<?> response = productCategoryController.getOne(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productCategorySevice, times(1)).getOne(1L);
    }

    @Test
    void testGetOne_NotFound() {
        when(productCategorySevice.getOne(999L)).thenThrow(new RuntimeException("Category Not Found"));

        ResponseEntity<?> response = productCategoryController.getOne(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productCategorySevice, times(1)).getOne(999L);
    }

    @Test
    void testUpdate_Success() {
        ProductCategory updatedCategory = new ProductCategory(1L, "Updated Category");
        when(productCategorySevice.update(1L, "Updated Category")).thenReturn(updatedCategory);

        ResponseEntity<?> response = productCategoryController.update(1L, "Updated Category");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(productCategorySevice, times(1)).update(1L, "Updated Category");
    }

    @Test
    void testUpdate_NotFound() {
        when(productCategorySevice.update(999L, "Updated Category")).thenThrow(new RuntimeException("Category Not Found"));

        ResponseEntity<?> response = productCategoryController.update(999L, "Updated Category");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productCategorySevice, times(1)).update(999L, "Updated Category");
    }

    @Test
    void testDelete_Success() {
        doNothing().when(productCategorySevice).delete(1L);

        ResponseEntity<?> response = productCategoryController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productCategorySevice, times(1)).delete(1L);
    }

    @Test
    void testDelete_NotFound() {
        doThrow(new RuntimeException("Category Not Found")).when(productCategorySevice).delete(999L);

        ResponseEntity<?> response = productCategoryController.delete(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productCategorySevice, times(1)).delete(999L);
    }
}