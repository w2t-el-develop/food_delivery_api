package project.fooddelivery.api.Cart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cart"
//        ,uniqueConstraints = {
//                @UniqueConstraint(name = "uq_cart", columnNames = {"cart_id", "customer_id"})
//        }
)
public class Cart {

    @Id
    @ColumnDefault("uuidv7()")
    @Column(name = "cart_id", nullable = false, updatable = false)
    private UUID cartId;
    @Column(name = "customer_id")
    private UUID customerId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    List<CartItem> cartItems;

}
