package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartItem addToCart(AddToCartRequest request) {
        Cart cart = cartRepository.findByCustomerId(request.customerId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerId(request.customerId());
                    return cartRepository.save(newCart);
                });

        CartItem newItem = CartItem.builder()
                .cart(cart)
                .menuItemId(request.cartItem().menuItemId())
                .quantity(request.cartItem().quantity())
                .price(request.cartItem().price())
                .build();

        return cartItemRepository.save(newItem);
    }
}
