package project.fooddelivery.api.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id", nullable = false)
    private List<OrderItem> orderItems = new ArrayList<>();

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus orderStatusId;

    public void addItem(OrderItem orderItem){
        orderItems.add(orderItem);
    }

    public void addItems(List<OrderItem> orderItems){
        if (orderItems == null) return;
        orderItems.forEach(this::addItem);
    }


}
