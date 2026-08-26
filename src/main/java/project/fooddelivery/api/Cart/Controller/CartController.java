package project.fooddelivery.api.Cart.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.fooddelivery.api.Cart.model.CartItem;
import project.fooddelivery.api.Cart.model.Cart;
import project.fooddelivery.api.Cart.service.CartItemService;
//import project.fooddelivery.api.feature.cart.CartService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("cart")
public class CartController {
//    private final CartService cartService;
//    private final CartItemService cartItemService ;
//    private Optional<Cart> currentCart;
//
//
//
//
//
//    public Optional<Cart> getCartyByCustomer(UUID customerId){
//        return  cartService.getCartByCustomerId(customerId);
//    }


//    @GetMapping("/{customerId}")
//    public List<CartItem> getCartItemByCustomer(@PathVariable("customerId") UUID customerId){
//        currentCart = getCartyByCustomer(customerId);
//
//        UUID currentCartId = currentCart.get().getCartId();
//        if (currentCartId != null){
//            List<CartItem> listCartItem = cartItemService.getByCartID(currentCartId);
//            return listCartItem;
//        }
//        return null ;
//    }
//
//    @GetMapping
//    public List<CartItem> getSomeCartItem(){
//        return cartItemService.getSomeCartItems();
//    }



}
