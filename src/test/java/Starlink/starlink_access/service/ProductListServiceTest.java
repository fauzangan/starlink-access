package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.ProductListDTO;
import Starlink.starlink_access.model.ProductList;
import Starlink.starlink_access.repository.ProductListRepository;
import Starlink.starlink_access.repository.ProductRepository;
import Starlink.starlink_access.repository.TransactionRepository;
import Starlink.starlink_access.service.implementation.ProductListServiceImplement;
import Starlink.starlink_access.util.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductListServiceTest {

    @Mock
    private ProductListRepository productListRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ProductListServiceImplement productListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        ProductListDTO productListDTO = new ProductListDTO();
        productListDTO.setProduct_id(1L);
        productListDTO.setTransaction_id(1L);
        productListDTO.setPrice(90000L);
        productListDTO.setQuantity(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(Helper.createProduct()));
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(Helper.createTransaction()));
        when(productListRepository.save(any(ProductList.class))).thenReturn(Helper.createProductList());

        ProductList result = productListService.create(productListDTO);

        assertNotNull(result);
        assertEquals(productListDTO.getPrice(), result.getPrice());
        assertEquals(productListDTO.getQuantity(), result.getQuantity());
        verify(productListRepository).save(any(ProductList.class));
    }

    @Test
    void testGetAll() {
        Pageable pageable = Pageable.unpaged();
        ProductListDTO productListDTO = new ProductListDTO();
        Page<ProductList> expectedPage = new PageImpl<>(Arrays.asList(Helper.createProductList()));

        when(productListRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<ProductList> result = productListService.getAll(pageable, productListDTO);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productListRepository).findAll(pageable);
    }

    @Test
    void testGetOne() {
        Long productListId = 1L;
        ProductList expectedProductList = Helper.createProductList();

        when(productListRepository.findById(productListId)).thenReturn(Optional.of(expectedProductList));

        ProductListDTO result = productListService.getOne(productListId);

        assertNotNull(result);
        assertEquals(expectedProductList.getId(), result.getId());
        assertEquals(expectedProductList.getPrice(), result.getPrice());
        assertEquals(expectedProductList.getQuantity(), result.getQuantity());
        verify(productListRepository).findById(productListId);
    }

    @Test
    void testUpdate() {
        Long productListId = 1L;
        ProductListDTO productListDTO = new ProductListDTO();
        productListDTO.setProduct_id(1L);
        productListDTO.setPrice(150000L);
        productListDTO.setQuantity(3L);

        ProductList existingProductList = Helper.createProductList();
        when(productListRepository.findById(productListId)).thenReturn(Optional.of(existingProductList));
        when(productRepository.findById(1L)).thenReturn(Optional.of(Helper.createProduct()));
        when(productListRepository.save(any(ProductList.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductListDTO result = productListService.update(productListId, productListDTO);

        assertNotNull(result);
        assertEquals(productListDTO.getPrice(), result.getPrice());
        assertEquals(productListDTO.getQuantity(), result.getQuantity());
        verify(productListRepository).save(any(ProductList.class));
    }

    @Test
    void testDelete() {
        Long productListId = 1L;

        productListService.delete(productListId);

        verify(productListRepository).deleteById(productListId);
    }

    @Test
    void testCreate_ProductNotFound() {
        ProductListDTO productListDTO = new ProductListDTO();
        productListDTO.setProduct_id(999L);
        productListDTO.setTransaction_id(1L);

        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(Helper.createTransaction()));

        assertThrows(RuntimeException.class, () -> productListService.create(productListDTO));
    }

    @Test
    void testCreate_TransactionNotFound() {
        ProductListDTO productListDTO = new ProductListDTO();
        productListDTO.setProduct_id(1L);
        productListDTO.setTransaction_id(999L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(Helper.createProduct()));
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productListService.create(productListDTO));
    }

    @Test
    void testGetOne_NotFound() {
        Long productListId = 999L;

        when(productListRepository.findById(productListId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productListService.getOne(productListId));
    }

    @Test
    void testUpdate_NotFound() {
        Long productListId = 999L;
        ProductListDTO productListDTO = new ProductListDTO();

        when(productListRepository.findById(productListId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productListService.update(productListId, productListDTO));
    }
}