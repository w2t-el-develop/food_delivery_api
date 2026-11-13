package project.fooddelivery.api.menu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu_item_image")
public class MenuItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "image_id", nullable = false, updatable = false)
    private UUID imageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_item_id", nullable = false, updatable = false)
    private MenuItem menuItem;

    @Column(name = "object_key", nullable = false, updatable = false, length = 512)
    private String objectKey;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    public MenuItemImage createMenuItemImage(MenuItem menuItem, String objectKey, int sortOrder, boolean isPrimary){
        return MenuItemImage.builder().
                menuItem(menuItem).objectKey(objectKey).sortOrder(sortOrder).isPrimary(isPrimary).build();
    }
}
