package project.fooddelivery.api.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @ColumnDefault("uuidv7()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", nullable = false, updatable = false)
    private UUID orderItemId;

    @Column(name = "menu_item_id", nullable = false, updatable = false)
    private UUID menuItemId;

    @Column(name = "order_item_quantity")
    private Integer orderItemQuantity;

    @Column(name = "order_item_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal orderItemPrice;
}
