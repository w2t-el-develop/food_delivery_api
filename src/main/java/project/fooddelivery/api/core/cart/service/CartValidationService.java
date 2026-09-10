package project.fooddelivery.api.core.cart.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.core.cart.repository.CartRepository;
import project.fooddelivery.api.utility.exceptionhandling.ResourceNotFoundException;

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
