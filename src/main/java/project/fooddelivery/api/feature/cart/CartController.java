package project.fooddelivery.api.feature.cart;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @PostMapping("/{customerId}/add")
    public ResponseEntity<CartItemResponse> addToCart(
            @PathVariable UUID customerId,
            @Valid @RequestBody AddToCartRequest request) {

        CartItem savedItem = cartService.addToCart(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartMapper.toCartItemResponse(savedItem));
    }
}
