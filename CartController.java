package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart") // Updated to match the documentation route
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartRequest request) {
        try {
            CartItem savedItem = cartService.addToCart(request);

            // Mapping the response data as per the standard response format in docs
            var responseData = Map.of(
                    "cart_item_id", savedItem.getCartItemId(),
                    "cart_id", savedItem.getCart().getCartId(),
                    "menu_item_id", savedItem.getMenuItemId(),
                    "cart_item_quantity", savedItem.getQuantity(),
                    "cart_item_price", savedItem.getPrice()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "message", "Item added to cart successfully",
                    "data", responseData
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            // Handling the "Item not found" case
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}