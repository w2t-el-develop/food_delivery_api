package project.fooddelivery.api.Cart.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.Cart.dto.UpdateQuantityDto;
import project.fooddelivery.api.Cart.model.CartItem;
import project.fooddelivery.api.Cart.service.CartItemService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("cartItem")
public class CartItemController {
    private final CartItemService cartItemService;


    @PatchMapping(path = "/{customerId}/{cartItemId}", consumes = "application/json")
    public ResponseEntity<CartItem> updateQuantityOfCartItem(@PathVariable("customerId") UUID customerId,@PathVariable("cartItemId") UUID cartItemId, @RequestBody UpdateQuantityDto updateQuantityDto){
        return cartItemService.getStatusOfUpdateCartItem(customerId, cartItemId, updateQuantityDto);
    }
}
