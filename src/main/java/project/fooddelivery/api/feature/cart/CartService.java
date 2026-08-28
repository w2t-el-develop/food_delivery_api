package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Transactional(readOnly = true)
    public CartWithItemsResponseDTO getCartByCustomerId(UUID customerId) {
        return cartRepository.findByCustomerId(customerId)
                .map(cart -> {
                    if (cart.getCartItems().isEmpty()) {
                        throw new CartEmptyException("Cart with ID " + cart.getCartId() + " for customer " + customerId + " is empty.");
                    }
                    return cartMapper.toCartWithItemsResponse(cart);
                })
                .orElseThrow(() -> new CartNotFoundException("Cart for customer " + customerId + " not found."));
    }

    @Transactional
    public BigDecimal deleteCartItem(UUID cartId, UUID itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ItemNotFoundException("Cart with ID " + cartId + " not found."));

        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getCartItemId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + itemId + " not found in cart " + cartId + "."));

        cart.getCartItems().remove(itemToRemove);
        cartRepository.save(cart);

        return cartMapper.calculateTotalPrice(cart);
    }

}
