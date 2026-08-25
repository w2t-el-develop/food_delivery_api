package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    // private final MenuItemRepository menuItemRepository; // Uncomment when you have MenuItem Module

    @Transactional
    public CartItem addToCart(AddToCartRequest request) {
        if (request.cartItem().cart_item_quantity() <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        // TODO: Fetch price from MenuItemRepository
        // BigDecimal price = menuItemRepository.findById(request.cartItem().menu_item_id())
        //         .orElseThrow(() -> new RuntimeException("Item not found"))
        //         .getPrice();
        BigDecimal price = new BigDecimal("25.50"); // Mocked price for now as per docs

        // 1. Get or Create Customer Cart
        Cart cart = cartRepository.findByCustomerId(request.customerId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerId(request.customerId());
                    return cartRepository.save(newCart);
                });

        // 2. Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndMenuItemId(cart, request.cartItem().menu_item_id());

        if (existingItem.isPresent()) {
            // Update Quantity
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.cartItem().cart_item_quantity());
            return cartItemRepository.save(item);
        } else {
            // Insert New Cart Item
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .menuItemId(request.cartItem().menu_item_id())
                    .quantity(request.cartItem().cart_item_quantity())
                    .price(price)
                    .build();
            return cartItemRepository.save(newItem);
        }
    }
}