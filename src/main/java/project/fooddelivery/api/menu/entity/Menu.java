package project.fooddelivery.api.menu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID menuId;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Column(name = "menu_category")
    private String menuCategory;

    @Column(name = "menu_code")
    private String menuCode;
}