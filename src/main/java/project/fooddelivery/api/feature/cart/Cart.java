package project.fooddelivery.api.feature.cart;

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
    @Column(name = "cart_id", nullable = false, updatable = false)
    private UUID cartId;
    @Column(name = "customer_id")
    private String customerId;

}
