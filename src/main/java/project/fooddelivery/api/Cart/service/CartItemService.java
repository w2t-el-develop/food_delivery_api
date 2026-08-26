package project.fooddelivery.api.Cart.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.Cart.Repository.CartItemRepository;
import project.fooddelivery.api.Cart.dto.UpdateQuantityDto;
import project.fooddelivery.api.Cart.model.Cart;
import project.fooddelivery.api.Cart.model.CartItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;

    public ResponseEntity<CartItem> getStatusOfUpdateCartItem(UUID customerId, UUID cartItemId, UpdateQuantityDto updateQuantityDto) {
        // return cart by CustomerId and if customer hasn't cart cartService throw exception
        Optional<Cart> cartByCustomerId = cartService.getByCustomerId(customerId);

        // get cartItems in cart
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartByCustomerId.get().getCartId());

        // check cart has cartItems
        if (cartItems.isEmpty()) throw new EntityNotFoundException("cart not has any cartItem");

        // get cartItem from cart's list of cart item
        Optional<CartItem> cartItemOpy = getCartItemFromList(cartItems, cartItemId);
        if (cartItemOpy.isEmpty()) throw new EntityNotFoundException("cart Item not found");
        /*
            here check cartItem is available in menu or not ?
         */
        // update quantity and change price of cartItem
        // double newTotal = priceOneItem * newQuantity ->  priceOneItem is get from menuItem

        // update quantity in cartItem
        CartItem cartItem = cartItemOpy.orElse(new CartItem());
        cartItem.setCartItemQuantity(updateQuantityDto.newQuantity());

        // save cartItem in database
        cartItemRepository.save(cartItem);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Optional<CartItem> getCartItemFromList(List<CartItem> listOfCartItem, UUID cartItemId) {
        for (CartItem cartItem : listOfCartItem){
            if ( cartItem.getCartItemId() == cartItemId){
                return Optional.of(cartItem);
            }
        }
        return Optional.empty();
    }
}
