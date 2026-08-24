package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService ;
    private Optional<Cart> currentCart;




    public Optional<Cart> getCartyByCustomer(UUID customerId){
        return  cartService.getCartByCustomerId(customerId);
    }


    @GetMapping("/{customerId}")
    public List<CartItem> getCartItemByCustomer(@PathVariable("customerId") UUID customerId){
        currentCart = getCartyByCustomer(customerId);

        UUID currentCartId = currentCart.get().getCartId();
        if (currentCartId != null){
            List<CartItem> listCartItem = cartItemService.getByCartID(currentCartId);
            return listCartItem;
        }
        return null ;
    }


}
