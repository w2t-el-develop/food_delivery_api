package project.fooddelivery.api.menu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @ColumnDefault("uuidv7()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_item_id", nullable = false, updatable = false)
    private UUID menuItemId;

    @Column(name = "menu_id", nullable = false, updatable = false)
    private UUID menuId;

    @Column(name = "menu_item_name", nullable = false)
    private String menuItemName;

    @Column(name = "menu_item_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal menuItemPrice;
}
