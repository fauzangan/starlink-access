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
    public ProductCategory create(String request) {
        ProductCategory category = ProductCategory.builder()
                .name(request)
                .build();

        return productCategoryRepository.save(category);
    }

    @Override
    public List<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory getOne(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category Not Found"));
        return productCategory;
    }

    @Override
    public ProductCategory update(Long id, String request) {
        ProductCategory update = getOne(id);;
        update.setName(request);
        return productCategoryRepository.save(update);
    }

    @Override
    public void delete(Long id) {
        ProductCategory delete = getOne(id);
        productCategoryRepository.delete(delete);
        }
}