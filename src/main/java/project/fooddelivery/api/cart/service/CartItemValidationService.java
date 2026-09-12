package project.fooddelivery.api.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.cart.infrastructure.projection.repository.CartItemRepository;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemValidationService {

    private final CartItemRepository cartItemRepository;

    public void isCartItemBelongsToCustomer(UUID cartItemId, UUID customerId) {
        boolean result = cartItemRepository.existsCartItemByCartItemIdAndCart_customerId(cartItemId, customerId);
        if(!result){
            throw new ResourceNotFoundException("Cart item not found");
        }
    }
}
