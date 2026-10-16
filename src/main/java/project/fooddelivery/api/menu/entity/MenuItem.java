package project.fooddelivery.api.menu.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NotNull
@Table(name = "menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID menuItemId;

    @Column(name = "menu_item_name")
    private String menuItemName;

    @Column(name = "menu_item_image_url")
    private String menuItemImageUrl;

    @Column(name = "menu_id")
    private UUID menuId;

    @Column(name = "menu_item_price")
    private Double menuItemPrice;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "menu_item_code")
    private String menuItemCode;
}