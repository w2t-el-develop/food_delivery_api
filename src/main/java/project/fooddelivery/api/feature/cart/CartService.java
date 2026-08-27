package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

  
 @Transactional(readOnly = true)
public CartIdResponseDTO getCartByCustomerId(String customerId) {
    return  cartRepository.findByCustomerId(UUID.fromString(customerId))
            .map(cart -> {
                if (cart.getCartItems().isEmpty()) {
                    throw new CartEmptyException(cart.getCartId(), customerId);
                }
                return cartMapper.toCartIdResponse(cart);
            })
            .orElseThrow(() -> new CartNotFoundException("Cart not found for customer: " + customerId));
}

   @Transactional(readOnly = true)
    public CartWithItemsResponseDTO getCartByCartId(String cartId) {
        UUID cartUUID = UUID.fromString(cartId);
        return cartRepository.findById(cartUUID)
                .map(cartMapper::toCartWithItemsResponse)
                .orElseThrow(() -> new CartNotFoundException("Cart with ID " + cartId + " not found."));    
    }




}
