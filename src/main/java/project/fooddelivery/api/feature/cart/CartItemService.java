package project.fooddelivery.api.feature.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getByCartID(UUID cartId){
        List<CartItem> cartItemsInCart = cartItemRepository.findByCartId(cartId);
         return cartItemsInCart;
    }
}
