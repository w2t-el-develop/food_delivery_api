package project.fooddelivery.api.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.fooddelivery.api.menu.entity.MenuItem;

import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    boolean isMenuItemAvailable(UUID menuItemId);

    Optional<MenuItem> getMenuItemByMenuItemId(UUID menuItemId);

    @Query("select menu_item from MenuItem menu_item where menu_item.menuItemId = :menuItemId and menu_item.isMenuItemAvailable = true ")
    Optional<MenuItem> getMenuItemByIdIfAvailable(UUID menuItemId);
}
