package Starlink.starlink_access.repository;

import Starlink.starlink_access.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    @Override
    Optional<ProductCategory> findById(Long aLong);
}
