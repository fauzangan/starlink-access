package Starlink.starlink_access.repository;

import Starlink.starlink_access.DTO.ProductDTO;
import Starlink.starlink_access.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long >, JpaSpecificationExecutor<Product> {
    @Override
    Optional<Product> findById(Long aLong);

}
