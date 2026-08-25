package project.fooddelivery.api.feature.cart;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
    name = "cart_item",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_cart_item", columnNames = {"cart_id", "menu_item_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @ColumnDefault("uuidv7()")
    @Column(name = "cart_item_id", nullable = false, updatable = false)
    private UUID cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "menu_item_id", nullable = false)
    private UUID menuItemId;

    @Column(name = "cart_item_quantity", nullable = false)
    private int quantity;

    @Column(name = "cart_item_price", nullable = false)
    private BigDecimal price;
}