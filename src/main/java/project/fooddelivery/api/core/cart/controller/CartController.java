package project.fooddelivery.api.core.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.fooddelivery.api.core.cart.dto.CartResponseDto;
import project.fooddelivery.api.core.cart.service.CartService;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("customers/{customerId}/cart/items")
public class CartController {

    private final CartService cartService;

    @GetMapping
    ResponseEntity<CartResponseDto> viewCart(@PathVariable UUID customerId) {
        CartResponseDto response = cartService.viewCart(customerId);
        return ResponseEntity.ok(response);
    }


}
