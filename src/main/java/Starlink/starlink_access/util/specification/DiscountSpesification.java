package Starlink.starlink_access.util.specification;

import Starlink.starlink_access.model.Discount;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DiscountSpesification {
    private static final Logger log = LoggerFactory.getLogger(DiscountSpesification.class);

    public static Specification<Discount> discountSpecification (String name, Integer percentage){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if (percentage != null){
                predicates.add(criteriaBuilder.equal(root.get("percentage"), percentage));
            }
            Predicate[] predicateArray = predicates.toArray(new Predicate[0]);
            return criteriaBuilder.and(predicateArray);
        };
    }
}
