package project.fooddelivery.api.core.cart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cart",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_cart_item", columnNames = {"cart_id", "menu_item_id"})
        }
)
public class Cart {

    @Id
    @ColumnDefault("uuidv7()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id", nullable = false, updatable = false)
    private UUID cartId;

    @Column(name = "customer_id")
    private UUID customerId;


}
