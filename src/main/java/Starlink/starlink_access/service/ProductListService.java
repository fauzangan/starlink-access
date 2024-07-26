package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.ProductListDTO;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.model.ProductList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductListService {
    ProductList create(ProductListDTO productDTO);
    Page<ProductList> getAll(Pageable pageable, ProductListDTO productListDTO);
    ProductListDTO getOne(Long id);
    ProductListDTO update(Long id, ProductListDTO productlistDTO);
    void delete(Long id);
}
