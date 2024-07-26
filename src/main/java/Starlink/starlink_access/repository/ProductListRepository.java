package Starlink.starlink_access.repository;

import Starlink.starlink_access.model.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Long> {

}
