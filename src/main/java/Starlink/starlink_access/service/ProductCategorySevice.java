package Starlink.starlink_access.service;

import Starlink.starlink_access.DTO.ProductCategoryDTO;
import Starlink.starlink_access.model.ProductCategory;

import java.util.List;

public interface ProductCategorySevice {
ProductCategory create(String request);
List<ProductCategory> getAll();
ProductCategory getOne(Long id);
ProductCategory update(Long id, String request);
void delete(Long id);

}
