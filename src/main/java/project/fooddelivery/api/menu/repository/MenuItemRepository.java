package project.fooddelivery.api.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.menu.entity.MenuItem;

import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    boolean isMenuItemAvailable(UUID menuItemId);

    Optional<MenuItem> getMenuItemByMenuItemId(UUID menuItemId);

    Optional<MenuItem> getMenuItemIfAvailable(UUID menuItemId);
}
