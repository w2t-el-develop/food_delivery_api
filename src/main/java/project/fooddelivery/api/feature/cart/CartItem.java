package project.fooddelivery.api.feature.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cart_item", uniqueConstraints = {
        @UniqueConstraint(name = "uq_cart_item", columnNames = {"cart_id", "cart_item_id", "menu_item_id"})
})
public class CartItem {
    @Id
    @ColumnDefault("uuidv7()")
    @Column(name = "cart_item_id", nullable = false, updatable = false)
    private UUID cartItemId;
    @Column(name = "cart_id", nullable = false)
    private UUID cartId;
    @Column(name = "menu_item_id", nullable = false)
    private UUID menuItemId;
    @Column(name = "cart_item_quantity", nullable = false)
    private int cartItemQuantity;
    @Column(name = "cart_item_price", nullable = false)
    private double cartItemPrice;
}
