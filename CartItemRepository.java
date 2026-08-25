package project.fooddelivery.api.feature.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Optional<CartItem> findByCartAndMenuItemId(Cart cart, UUID menuItemId);
}