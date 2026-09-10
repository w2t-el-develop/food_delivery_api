package project.fooddelivery.api.core.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.core.cart.dto.CartItemResponseDto;
import project.fooddelivery.api.core.cart.dto.RemoveCartItemRequestDto;
import project.fooddelivery.api.core.cart.dto.UpdateQuantityRequestDto;
import project.fooddelivery.api.core.cart.mapper.CartItemMapper;
import project.fooddelivery.api.core.cart.model.CartItem;
import project.fooddelivery.api.core.cart.service.CartItemService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("cart-items/customer/{customerId}")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartItemMapper cartItemMapper;

    @PatchMapping("{cartItemId}")
    ResponseEntity<CartItemResponseDto> updateCartItemQuantity(@PathVariable UUID customerId,
                                                               @PathVariable UUID cartItemId,
                                                               @Valid @RequestBody UpdateQuantityRequestDto updateQuantityRequestDto) {

        CartItem cartItem = cartItemService.UpdateCartItemQuantity(customerId, cartItemId, updateQuantityRequestDto);

        return ResponseEntity.ok(cartItemMapper.toResponseDto(cartItem));
    }

    @DeleteMapping("{cartItemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable UUID customerId,
            @PathVariable UUID cartItemId) {

        cartItemService.removeItem(customerId, cartItemId);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("batch-delete")
    public ResponseEntity<Void> batchRemoveItems(
            @PathVariable UUID customerId,
            @Valid @RequestBody RemoveCartItemRequestDto removeCartItemRequestDto) {

        cartItemService.batchRemoveItems(customerId, removeCartItemRequestDto);

        return ResponseEntity.noContent().build();
    }

}
