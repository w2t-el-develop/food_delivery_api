package project.fooddelivery.api.feature.cart;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @Column(name = "cart_item_id")
    private UUID cartItemId;

    @Column(name = "cart_id")
    private UUID cartId;

    @Column(name = "menu_item_id")
    private UUID menuItemId;

    @Column(name = "cart_item_quantity")
    private int quantity;

    @Column(name = "cart_item_price", precision = 12, scale = 2)
    private BigDecimal cartItemPrice;

}