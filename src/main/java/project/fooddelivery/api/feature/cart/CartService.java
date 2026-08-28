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
    public CartWithItemsResponseDTO getCartByCustomerId(UUID customerId) {
        return cartRepository.findByCustomerId(customerId)
                .map(cart -> {
                    if (cart.getCartItems().isEmpty()) {
                        throw new CartEmptyException(cart.getCartId(), customerId);
                    }
                    return cartMapper.toCartWithItemsResponse(cart);
                })
                .orElseThrow(() -> new CartNotFoundException(customerId));
    }

}
