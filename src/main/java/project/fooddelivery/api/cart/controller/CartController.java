package project.fooddelivery.api.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.cart.dto.AddToCartRequestDto;
import project.fooddelivery.api.cart.dto.CartItemResponseDto;
import project.fooddelivery.api.cart.dto.CartResponseDto;
import project.fooddelivery.api.cart.mapper.CartItemMapper;
import project.fooddelivery.api.cart.entity.CartItem;
import project.fooddelivery.api.cart.service.CartService;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("carts/customers/{customer-id}")
public class CartController {

    private final CartService cartService;
    private final CartItemMapper cartItemMapper;

    @GetMapping
    ResponseEntity<CartResponseDto> viewCart(@PathVariable("customer-id") UUID customerId) {
        CartResponseDto cartResponseDto = cartService.viewCart(customerId);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PostMapping
    ResponseEntity<CartItemResponseDto> addToCart(@PathVariable("customer-id") UUID customerId,
                                                  @Valid @RequestBody AddToCartRequestDto addToCartRequestDto) {
        CartItem cartItem = cartService.addToCart(customerId, addToCartRequestDto);
        return new ResponseEntity<>(cartItemMapper.toResponseDto(cartItem), HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @PathVariable("customer-id") UUID customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }

}
