package project.fooddelivery.api.core.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.core.cart.dto.AddToCartRequestDto;
import project.fooddelivery.api.core.cart.dto.CartItemResponseDto;
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

    @PostMapping
    ResponseEntity<CartItemResponseDto> addToCart(@PathVariable UUID customerId, AddToCartRequestDto addToCartRequestDto) {
        CartItemResponseDto response = cartService.addToCart(customerId, addToCartRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



}
