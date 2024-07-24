package Starlink.starlink_access.service.implementation;
import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.mapper.ProductCategoryMapper;
import Starlink.starlink_access.mapper.ProductMapper;
import Starlink.starlink_access.model.Product;
import Starlink.starlink_access.repository.ProductRepository;
import Starlink.starlink_access.service.ProductSevice;
import Starlink.starlink_access.util.specification.GeneralSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductSevice {
    private final ProductRepository productRepository;


    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product product = ProductMapper.map(productDTO);
        productRepository.save(product);
        return ProductMapper.map(product);
    }

    @Override
    public Page<Product> getAll(Pageable pageable, ProductDTO productDTO) {
        Specification<Product> specification = GeneralSpecification.getSpecification(productDTO);
        Page<Product> products = productRepository.findAll(specification, pageable);
        return products;
    }

    @Override
    public ProductDTO getOne(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        return ProductMapper.map(product);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDto) {
        ProductDTO productDTO = getOne(id);
        Product update = ProductMapper.map(productDTO);
        update.setPrice(productDto.getPrice());
        update.setName(productDto.getName());
        update.setStock(productDto.getStock());
        update.setProductCategory(ProductCategoryMapper.map(productDto.getProductCategory()));
        update.setPicture_source(productDto.getPicture_source());
        productRepository.save(update);
        return ProductMapper.map(update);
    }

    @Override
    public void delete(Long id) {
        Product delete = ProductMapper.map(getOne(id));
        productRepository.delete(delete);
    }

}
