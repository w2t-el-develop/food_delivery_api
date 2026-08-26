package project.fooddelivery.api.Cart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.Cart.model.CartItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findAllByCartId(UUID cartId);
}
