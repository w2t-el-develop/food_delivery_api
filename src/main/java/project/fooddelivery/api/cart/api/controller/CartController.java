package project.fooddelivery.api.cart.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.cart.dto.request.AddToCartRequestDto;
import project.fooddelivery.api.cart.dto.response.CartItemResponseDto;
import project.fooddelivery.api.cart.dto.response.CartResponseDto;
import project.fooddelivery.api.cart.mapper.CartItemMapper;
import project.fooddelivery.api.cart.infrastructure.projection.entity.CartItem;
import project.fooddelivery.api.cart.service.CartService;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("carts/customers/{customerId}")
public class CartController {

    private final CartService cartService;
    private final CartItemMapper cartItemMapper;

    @GetMapping
    ResponseEntity<CartResponseDto> viewCart(@PathVariable UUID customerId) {
        CartResponseDto cartResponseDto = cartService.viewCart(customerId);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PostMapping
    ResponseEntity<CartItemResponseDto> addToCart(@PathVariable UUID customerId,
                                                  @Valid @RequestBody AddToCartRequestDto addToCartRequestDto) {
        CartItem cartItem = cartService.addToCart(customerId, addToCartRequestDto);
        return new ResponseEntity<>(cartItemMapper.toResponseDto(cartItem), HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @PathVariable UUID customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }

}
