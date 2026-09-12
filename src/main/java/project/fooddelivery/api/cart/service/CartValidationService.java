package project.fooddelivery.api.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.cart.infrastructure.projection.repository.CartRepository;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CartValidationService {

    private final CartRepository cartRepository;

    public void isCartBelongsToCustomer(UUID customerId){
        boolean result = cartRepository.existsByCustomerId(customerId);
        if(!result){
            throw new ResourceNotFoundException("Cart not found");
        }
    }

}
