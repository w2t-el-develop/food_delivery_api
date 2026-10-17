package project.fooddelivery.api.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @ColumnDefault("uuidv7()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", nullable = false, updatable = false)
    private UUID orderId;

    @Column(name = "customer_id", nullable = false, updatable = false)
    private UUID customerId;

    @Column(name = "restaurant_id",  nullable = false, updatable = false)
    private UUID restaurantId;

    @Column(name = "order_date",  nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column(name = "address_id", nullable = false)
    private UUID addressId;

    @Positive(message = "Total order price must be greater than zero")
    @Column(name = "order_total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal orderTotalPrice;

    @OneToOne
    @JoinColumn(name = "order_status_id", unique = true)
    private OrderStatus orderStatusId;

}
