package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.OnlyForProductDTO;
import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.mapper.ProductMapper;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.model.ProductCategory;
import Starlink.starlink_access.repository.ProductRepository;
import Starlink.starlink_access.service.implementation.ProductServiceImplement;
import Starlink.starlink_access.util.response.CloudinaryResponse;
import Starlink.starlink_access.util.specification.FileUploadUtil;
import Starlink.starlink_access.util.specification.GeneralSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategorySevice productCategorySevice;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private ProductServiceImplement productService;

    private Product product;
    private OnlyForProductDTO onlyForProductDTO;
    private ProductCategory productCategory;
    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        productCategory = ProductCategory.builder()
                .id(1L)
                .name("Broadband")
                .build();

        product = Product.builder()
                .id(1L)
                .name("High Speed 200 Gb")
                .stock(10L)
                .price(200000000L)
                .picture_source("http://image")
                .productCategory(productCategory)
                .build();

        productDTO = ProductDTO.builder()
                .id(1L)
                .name("High Speed 200 Gb")
                .stock(10L)
                .price(200000000L)
                .picture_source("http://image")
                .productCategory(ProductCategoryDTO.builder().id(1L).name("Broadband").build())
                .build();

        onlyForProductDTO = OnlyForProductDTO.builder()
                .productCategory_id(1L)
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .picture_source(productDTO.getPicture_source())
                .name(productDTO.getName())
                .build();
    }

    @DisplayName("JUnit test for create product")
    @Test
    public void givenOnlyForProductDTO_whenCreate_thenReturnProduct() {
        given(productCategorySevice.getOne(anyLong())).willReturn(productCategory);
        given(productRepository.save(any(Product.class))).willReturn(product);

        Product savedProduct = productService.create(onlyForProductDTO);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo(onlyForProductDTO.getName());
        verify(productRepository).save(any(Product.class));
    }

    @DisplayName("JUnit test for get all products")
    @Test
    public void givenPageableAndProductDTO_whenGetAll_thenReturnPageOfProducts() {
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
        given(productRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(productPage);

        Page<Product> result = productService.getAll(Pageable.unpaged(), productDTO);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0)).isEqualTo(product);
        verify(productRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @DisplayName("JUnit test for get one product")
    @Test
    public void givenProductId_whenGetOne_thenReturnProductDTO() {
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));

        ProductDTO result = productService.getOne(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productDTO.getId());
        verify(productRepository).findById(1L);
    }

    @DisplayName("JUnit test for update product")
    @Test
    public void givenProductDTO_whenUpdate_thenReturnUpdatedProduct() {
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productCategorySevice.getOne(anyLong())).willReturn(productCategory);
        given(productRepository.save(any(Product.class))).willReturn(product);

        Product updatedProduct = productService.update(1L, onlyForProductDTO);

        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo(product.getName());
        assertThat(updatedProduct.getPrice()).isEqualTo(product.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @DisplayName("JUnit test for delete product")
    @Test
    public void givenProductId_whenDelete_thenVerifyDeletion() {
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));

        productService.delete(1L);

        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @DisplayName("JUnit test for upload image")
    @Test
    public void givenMultipartFile_whenUploadImage_thenUpdateProductImage() {

    }
}
