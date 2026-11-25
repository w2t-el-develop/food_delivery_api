package project.fooddelivery.api.menu.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_id", nullable = false)
    private UUID menuId;

    @Column(name = "menu_name", nullable = false, unique = true, length = 100)
    private String menuName;

    @Column(name = "menu_description")
    private String menuDescription;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "menu")
    private List<MenuItem> menuItems = new ArrayList<>();

    public void addItem(MenuItem menuItem){
        menuItems.add(Objects.requireNonNull(menuItem));
        menuItem.setMenu(this);
    }

    public static Menu createMenu(String menuName, String menuDescription){
        return Menu.builder().menuName(menuName).menuDescription(menuDescription).build();
    }


}
