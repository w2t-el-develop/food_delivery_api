package project.fooddelivery.api.core.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.core.cart.dto.*;
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

    @PostMapping
    ResponseEntity<CartItemResponseDto> addToCart(@PathVariable UUID customerId, AddToCartRequestDto addToCartRequestDto) {
        CartItemResponseDto response = cartService.addToCart(customerId, addToCartRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("{cartItemId}")
    ResponseEntity<CartItemResponseDto> updateCartItemQuantity(@PathVariable UUID customerId,
                                                               @PathVariable UUID cartItemId,
                                                               @Valid @RequestBody UpdateQuantityRequestDto updateQuantityRequestDto) {
        CartItemResponseDto response = cartService.UpdateCartItemQuantity(customerId, cartItemId, updateQuantityRequestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{cartItemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable UUID customerId,
            @PathVariable UUID cartItemId) {

        cartService.removeItem(
                customerId,
                cartItemId
        );

        return ResponseEntity.noContent().build();
    }

    @PostMapping("batch-delete")
    public ResponseEntity<Void> batchRemoveItems(
            @PathVariable UUID customerId,
            @Valid @RequestBody RemoveCartItemRequestDto removeCartItemRequestDto) {

        cartService.batchRemoveItems(customerId, removeCartItemRequestDto);

        return ResponseEntity.noContent().build();
    }


}
