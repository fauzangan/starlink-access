package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.OnlyForProductDTO;
import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductSevice {
    Product create(OnlyForProductDTO productDTO);
    Page<Product> getAll(Pageable pageable, ProductDTO productDTO);
    ProductDTO getOne(Long id);
    Product update(Long id, OnlyForProductDTO productDto);
    void delete (Long id);
    void uploadImage(MultipartFile file, Long id);
}
