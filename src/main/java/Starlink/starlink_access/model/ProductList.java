package Starlink.starlink_access.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "service_detail")
@AllArgsConstructor
@NoArgsConstructor
public class ProductList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;

    @JoinColumn(name = "product_id",nullable = false)
    @ManyToOne
    private Product product;

    @JoinColumn(name = "transaction_id",nullable = false)
    @ManyToOne
    private Transaction transaction;
}
