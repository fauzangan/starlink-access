package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.OnlyForProductDTO;
import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.mapper.ProductCategoryMapper;
import Starlink.starlink_access.mapper.ProductMapper;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.model.ProductCategory;
import Starlink.starlink_access.repository.ProductRepository;
import Starlink.starlink_access.service.implementation.ProductServiceImplement;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImplement productSevice;


    private Product product;
    private ProductDTO productDTO;
    private ProductCategoryDTO productCategoryDTO;

    @BeforeEach
    public void setUp() {
        productCategoryDTO = ProductCategoryDTO.builder()
                .name("High Speed")
                .id(1L)
                .build();
        productDTO = ProductDTO.builder()
                .id(1L)
                .picture_source("https://")
                .price(213254634L)
                .stock(20L)
                .name("always connect")
                .productCategory(productCategoryDTO)
                .build();
        product = Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .stock(productDTO.getStock())
                .productCategory(ProductCategoryMapper.map(productDTO.getProductCategory()))
                .price(productDTO.getPrice())
                .picture_source(productDTO.getPicture_source())
                .build();

    }

    @DisplayName("JUnit test for Create Product")
    @Test
    public void givenProductDTO_whenSave_thenSuccesSave() {

    }
    @DisplayName("JUnit test for Get All Product")
    @Test
    public void getAllProduct_succes(){
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        productList.add(new Product());

        Page<Product> products = new PageImpl<>(productList);

        given(productRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(products);

        // Testing the service method
        Page<Product>  productPage = productSevice.getAll(Pageable.unpaged(), productDTO);

        // Verifying that the repository method was called with the expected parameters
        verify(productRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));

        // Verifying that the result matches the mock data
        assertEquals(products.getTotalElements(), productPage.getTotalElements());
        assertEquals(products, productPage);



    }

    @DisplayName("JUnit test for Get the Product")
    @Test
    public void getTheProduct_succes(){
        given(productRepository.findById(1L)).willReturn(Optional.ofNullable(product));

        Product result = ProductMapper.map(productSevice.getOne(1L));

        assertNotNull(result);
        assertEquals(product,result);


    }

    @DisplayName("JUnit test for Update the Product")
    @Test
    public void UpdateTheProduct_succes(){

        assertNotNull(product);
    }

    @DisplayName("JUnit test for Delete the Product")
    @Test
    public void deleteTheProduct_succes(){

    }

}
