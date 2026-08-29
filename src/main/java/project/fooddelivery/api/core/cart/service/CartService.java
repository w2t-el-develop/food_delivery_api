package project.fooddelivery.api.core.cart.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.core.cart.dto.CartItemResponseDto;
import project.fooddelivery.api.core.cart.dto.CartResponseDto;
import project.fooddelivery.api.core.cart.model.Cart;
import project.fooddelivery.api.core.cart.repository.CartItemRepository;
import project.fooddelivery.api.core.cart.repository.CartRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartResponseDto viewCart(UUID customerId) {
        Cart cart = getCart(customerId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        UUID cartId = cart.getCartId();
        return CartResponseDto.builder()
                .cartId(cartId)
                .cartItems(getCartItems(cartId))
                .build();
    }

    Optional<Cart> getCart(UUID customerId) {
        return cartRepository.getCartByCustomerId(customerId);
    }

    private List<CartItemResponseDto> getCartItems(UUID CartID) {
        return cartItemRepository.getCartItemsByCart_CartId(CartID);
    }

}
