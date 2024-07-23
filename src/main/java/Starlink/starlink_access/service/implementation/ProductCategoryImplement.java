package Starlink.starlink_access.service.implementation;
import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.mapper.ProductCategoryMapper;
import Starlink.starlink_access.model.ProductCategory;
import Starlink.starlink_access.repository.ProductCategoryRepository;
import Starlink.starlink_access.service.ProductCategorySevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryImplement implements ProductCategorySevice {
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategoryDTO create(ProductCategoryDTO request) {
        ProductCategory category = ProductCategoryMapper.map(request);
        productCategoryRepository.save(category);
        return ProductCategoryMapper.map(category);
    }

    @Override
    public List<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategoryDTO getOne(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category Not Found"));
        return ProductCategoryMapper.map(productCategory);
    }

    @Override
    public ProductCategoryDTO update(Long id, ProductCategoryDTO request) {
        ProductCategoryDTO productCategoryDTO = getOne(id);
        ProductCategory update = ProductCategoryMapper.map(productCategoryDTO);
        update.setName(request.getName());
        return ProductCategoryMapper.map(update);
    }

    @Override
    public void delete(Long id) {
        ProductCategoryDTO productCategoryDTO = getOne(id);
        ProductCategory delete = ProductCategoryMapper.map(productCategoryDTO);
        productCategoryRepository.delete(delete);
        }
}