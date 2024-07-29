package Starlink.starlink_access.controller;

import Starlink.starlink_access.DTO.OnlyForProductDTO;
import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.service.CloudinaryService;
import Starlink.starlink_access.util.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductSevice productService;

    @Mock
    private CloudinaryService cloudinaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct_Success() {
        // Mock ProductService
        OnlyForProductDTO request = new OnlyForProductDTO();
        request.setName("Test Product");
        request.setPrice(10L);
        request.setStock(100L);

        Product expectedProduct = new Product();
        expectedProduct.setName(request.getName());
        expectedProduct.setPrice(request.getPrice());
        expectedProduct.setStock(request.getStock());

        Mockito.when(productService.create(request)).thenReturn(expectedProduct);

        // Perform the test
        ResponseEntity<?> response = productController.create(request);

        // Verify response status and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateProduct_Exception() {
        // Mock ProductService
        OnlyForProductDTO request = new OnlyForProductDTO();
        request.setName("Test Product");
        request.setPrice(10L);
        request.setStock(100L);

        Mockito.when(productService.create(request)).thenThrow(new RuntimeException("Test Exception"));

        // Perform the test
        ResponseEntity<?> response = productController.create(request);

        // Verify response status and body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllProducts_Success() {
        // Mock ProductService
        Pageable pageable = Pageable.unpaged();
        ProductDTO searchDTO = new ProductDTO();

        Page<Product> expectedProducts = Mockito.mock(Page.class);
        Mockito.when(productService.getAll(pageable, searchDTO)).thenReturn(expectedProducts);

        // Perform the test
        ResponseEntity<?> response = productController.getAll(pageable, searchDTO);

        // Verify response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllProducts_Exception() {
        // Mock ProductService
        Pageable pageable = Pageable.unpaged();
        ProductDTO searchDTO = new ProductDTO();

        Mockito.when(productService.getAll(pageable, searchDTO)).thenThrow(new RuntimeException("Test Exception"));

        // Perform the test
        ResponseEntity<?> response = productController.getAll(pageable, searchDTO);

        // Verify response status and body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
