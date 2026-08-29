package project.fooddelivery.api.core.cart.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@Table(name="cart_item")
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {


    @Id
    @ColumnDefault("uuidv7()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_item_id", nullable = false, updatable = false)
    private UUID cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "menu_item_id")
    private UUID menuItemId;

    @Column(name = "cart_item_quantity")
    private Integer cartItemQuantity;

    @Column(name = "cart_item_price")
    Double cartItemPrice;
}