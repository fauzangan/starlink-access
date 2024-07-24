package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductSevice {
    ProductDTO create(ProductDTO productDTO);
    Page<Product> getAll(Pageable pageable, ProductDTO productDTO);
    ProductDTO getOne(Long id);
    ProductDTO update(Long id, ProductDTO productDto);
    void delete (Long id);
}
