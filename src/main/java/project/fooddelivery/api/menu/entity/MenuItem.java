package project.fooddelivery.api.menu.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_item_id", nullable = false, updatable = false)
    private UUID menuItemId;

    @Column(name = "menu_item_name", nullable = false)
    private String menuItemName;

    @Column(name = "menu_item_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal menuItemPrice;

    @Column(name = "is_menu_item_available")
    private boolean isMenuItemAvailable;

    public static MenuItem buildMenuItem(String menuItemName, BigDecimal menuItemPrice, boolean isMenuItemAvailable){
        return MenuItem.builder().menuItemName(menuItemName)
                .menuItemPrice(menuItemPrice).isMenuItemAvailable(isMenuItemAvailable).build();
    }
}
