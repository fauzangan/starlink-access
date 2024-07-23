package Starlink.starlink_access.service;

import Starlink.starlink_access.model.ProductCategory;

import java.util.List;

public interface ProductCategorySevice {
ProductCategory create(String name);
List<ProductCategory> getAll();
ProductCategory getOne(Long id);
ProductCategory update(Long id, String name);
void delete(Long id);

}
