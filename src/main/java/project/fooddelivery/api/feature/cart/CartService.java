package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;

    @Transactional
    public CartItem addToCart(UUID customerId, AddToCartRequest request) {
        var menuItem = menuItemRepository.findById(request.menuItemId())
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerId(customerId);
                    return cartRepository.save(newCart);
                });

        CartItem newItem = CartItem.builder()
                .cart(cart)
                .menuItemId(request.menuItemId())
                .quantity(request.quantity())
                .price(menuItem.getPrice())
                .build();

        return cartItemRepository.save(newItem);
    }
}
