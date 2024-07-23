package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.model.ProductCategory;

import java.util.List;

public interface ProductCategorySevice {
ProductCategoryDTO create(String name);
List<ProductCategoryDTO> getAll();
ProductCategoryDTO getOne(Long id);
ProductCategoryDTO update(Long id, String name);
void delete(Long id);

}
