package Starlink.starlink_access.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    private Long total_price;

    private Long created_at;

    private Long updated_at;

    private Long expired_date;

    private Boolean is_settled;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "transaction")
    private List<ProductList> productLists;

    @JoinColumn(name = "discount_id",nullable = false)
    @ManyToOne
    private Discount discount;
}
