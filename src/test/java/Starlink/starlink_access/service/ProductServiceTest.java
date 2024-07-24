package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.DTO.ProductDTO;
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
import org.springframework.test.context.ActiveProfiles;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    }

    @DisplayName("JUnit test for Create Product")
    @Test
    public void givenProductDTO_whenSave_thenSuccesSave() {
        given(productRepository.save(Mockito.any(Product.class))).willReturn(any(Product.class));

        Product product = ProductMapper.map(productSevice.create(productDTO));
        assertNotNull(product);
        verify(productRepository, times(1)).save(any(Product.class));
    }
    @DisplayName("JUnit test for Get All Product")
    @Test
    public void getAllProduct_succes(){

    }

    @DisplayName("JUnit test for Get the Product")
    @Test
    public void getTheProduct_succes(){

    }

    @DisplayName("JUnit test for Update the Product")
    @Test
    public void UpdateTheProduct_succes(){

    }

    @DisplayName("JUnit test for Delete the Product")
    @Test
    public void deleteTheProduct_succes(){

    }

}
