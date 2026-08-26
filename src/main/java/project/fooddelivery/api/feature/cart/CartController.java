package project.fooddelivery.api.feature.cart;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @PostMapping("/add")
    public ResponseEntity<CartItemResponse> addToCart(@Valid @RequestBody AddToCartRequest request) {
        CartItem savedItem = cartService.addToCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartMapper.toCartItemResponse(savedItem));
    }
}
