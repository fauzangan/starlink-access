package Starlink.starlink_access.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long transaction_date;

    private Long expired_date;

    private Boolean is_settled;

    @JoinColumn(name = "service_detail_id",nullable = false)
    @ManyToOne
    private Statistic serviceDetail;

    @JoinColumn(name = "discount_id",nullable = false)
    @ManyToOne
    private Discount discount;
}
