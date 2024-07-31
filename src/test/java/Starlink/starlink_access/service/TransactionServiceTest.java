package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.BankTransferDTO;
import Starlink.starlink_access.DTO.ProductListDTO;
import Starlink.starlink_access.DTO.TransactionDTO;
import Starlink.starlink_access.DTO.TransactionDetailDTO;
import Starlink.starlink_access.mapper.ProductMapper;
import Starlink.starlink_access.model.*;
import Starlink.starlink_access.repository.*;
import Starlink.starlink_access.service.MidtransService;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.service.implementation.ProductListServiceImplement;
import Starlink.starlink_access.service.implementation.TransactionServiceImplement;
import Starlink.starlink_access.util.Helper;
import Starlink.starlink_access.util.request.MidtransRequest;
import Starlink.starlink_access.util.response.MidtransResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ProductListServiceImplement productListServiceImplement;
    @Mock
    private ProductListRepository productListRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MidtransService midtransService;
    @Mock
    private ProductSevice productSevice;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private DiscountRepository discountRepository;
    @Mock
    private AuthService authService;
    @Mock
    private ProductListService productListService;


    @InjectMocks
    private TransactionServiceImplement transactionService;

    private TransactionDTO transactionDTO;
    private User user;
    private Discount discount;
    private Product product;
    private ProductListDTO productListDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = Helper.createUser();
        discount = Helper.createDiscount();
        product = Helper.createProduct();

        transactionDTO = new TransactionDTO();
        transactionDTO.setDiscount(1L);
        transactionDTO.setPayment_type("bank_transfer");

        BankTransferDTO bankTransferDTO = new BankTransferDTO("BCA");
//        bankTransferDTO.setBank("bca");
        transactionDTO.setBank_transfer(bankTransferDTO);

        ProductListDTO productListDTO = new ProductListDTO();
        productListDTO.setProduct_id(1L);
        productListDTO.setQuantity(1L);

        transactionDTO.setProductLists(Collections.singletonList(productListDTO));

    }

    @Test
    void testCreate() throws Exception {
//        TransactionDTO transactionDTO = new TransactionDTO();
//        transactionDTO.setUser_id(1L);
//        transactionDTO.setDiscount(10L);
//        transactionDTO.setExpired_date("2024-07-01");
//        transactionDTO.setPayment_type("bank_transfer");
//        transactionDTO.setVirtualNumber("33141431");
//
//        ProductListDTO productListDTO = new ProductListDTO();
//        productListDTO.setProduct_id(1L);
//        productListDTO.setQuantity(2L);
//        transactionDTO.setProductLists(Arrays.asList(productListDTO));
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(Helper.createUser()));
//        when(discountRepository.findById(10L)).thenReturn(Optional.of(Helper.createDiscount()));
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(Helper.createTransaction());
//        when(productSevice.getOne(1L)).thenReturn(ProductMapper.map(Helper.createProduct()));
//        when(productListServiceImplement.create(any(ProductListDTO.class))).thenReturn(Helper.createProductList());
//        when(midtransService.chargeTransaction(any(MidtransRequest.class))).thenReturn(new MidtransResponse());
//
//        TransactionDTO result = transactionService.create(transactionDTO);
//
//        assertNotNull(result);
//        verify(transactionRepository, times(2)).save(any(Transaction.class));
//        verify(midtransService).chargeTransaction(any(MidtransRequest.class));
//
//        Long userId = 10L;
//        Long discountId = 5L;
//        Long productId = 1L;
//        Long quantity = 2L;

        when(authService.getUserAuthenticated()).thenReturn(user);
        when(discountRepository.findById(anyLong())).thenReturn(Optional.of(discount));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            transaction.setId(1L);
            return transaction;
        });

        when(productListService.create(any(ProductListDTO.class))).thenAnswer(invocation -> {
            ProductListDTO dto = invocation.getArgument(0);
            if (dto.getQuantity() > product.getStock()) {
                throw new RuntimeException("Product quantity exceeds stock");
            }
            ProductList productList = ProductList.builder()
                    .id(1L)
                    .product(product)
                    .transaction(Helper.createTransaction())
                    .price(dto.getQuantity() * product.getPrice())
                    .quantity(dto.getQuantity())
                    .build();
            return productList;
        });

        MidtransResponse midtransResponse = new MidtransResponse();
        midtransResponse.setTransactionId("1");
        midtransResponse.setTransactionStatus("pending");
        when(midtransService.chargeTransaction(any(MidtransRequest.class))).thenReturn(midtransResponse);

        TransactionDTO result = transactionService.create(transactionDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("pending", result.getTransaction_status());
        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testGetAll() {
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(Helper.createTransaction()));

        var result = transactionService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(transactionRepository).findAll();
    }

    @Test
    void testGetTransactionById() {
        Long transactionId = 1L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(Helper.createTransaction()));

        TransactionDTO result = transactionService.getTransactionById(transactionId);

        assertNotNull(result);
        assertEquals(transactionId, result.getId());
        verify(transactionRepository).findById(transactionId);
    }

    @Test
    void testFetchTransactionStatus() throws Exception {
        Long transactionId = 1L;
        MidtransResponse midtransResponse = new MidtransResponse();
        midtransResponse.setTransactionStatus("settlement");

        when(midtransService.fetchTransaction(transactionId)).thenReturn(midtransResponse);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(Helper.createTransaction()));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(Helper.createTransaction());

        TransactionDTO result = transactionService.fetchTransactionStatus(transactionId);

        assertNotNull(result);
        assertEquals("settlement", result.getTransaction_status());
        verify(midtransService).fetchTransaction(transactionId);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testUpdateTransaction() {
        Long transactionId = 1L;
        String status = "settlement";
        MidtransResponse midtransResponse = new MidtransResponse();
        midtransResponse.setTransactionStatus(status);

        when(midtransService.updateTransactionStatus(transactionId, status)).thenReturn(midtransResponse);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(Helper.createTransaction()));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(Helper.createTransaction());

        TransactionDTO result = transactionService.updateTransaction(transactionId, status);

        assertNotNull(result);
        assertEquals(status, result.getTransaction_status());
        verify(midtransService).updateTransactionStatus(transactionId, status);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testDelete() {
        Long transactionId = 1L;
        when(productListRepository.findByTransactionId(transactionId)).thenReturn(Arrays.asList(Helper.createProductList()));

        transactionService.delete(transactionId);

        verify(productListRepository).findByTransactionId(transactionId);
        verify(productListRepository).delete(any());
        verify(transactionRepository).deleteById(transactionId);
    }

    @Test
    void testCreate_UserNotFound() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUser_id(999L);

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.create(transactionDTO));
    }

    @Test
    void testCreate_DiscountNotFound() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUser_id(1L);
        transactionDTO.setDiscount(999L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(Helper.createUser()));
        when(discountRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.create(transactionDTO));
    }

    @Test
    void testGetTransactionById_NotFound() {
        Long transactionId = 999L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.getTransactionById(transactionId));
    }

    @Test
    void testFetchTransactionStatus_NotFound() {
        Long transactionId = 999L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.fetchTransactionStatus(transactionId));
    }

    @Test
    void testUpdateTransaction_NotFound() {
        Long transactionId = 999L;
        String status = "settlement";
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.updateTransaction(transactionId, status));
    }
}
