package project.fooddelivery.api.core.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.core.cart.dto.CartItemResponseDto;
import project.fooddelivery.api.core.cart.model.CartItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItemResponseDto> getCartItemsByCart_CartId(UUID cartId);

    Optional<CartItem> getCartItemByCart_cartIdAndMenuItemId(UUID cartItemId, UUID menuItemId);

}
