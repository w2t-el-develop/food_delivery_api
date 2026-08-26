package project.fooddelivery.api.Cart.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.Cart.Repository.CartRepository;
import project.fooddelivery.api.Cart.model.Cart;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    public Optional<Cart> getByCustomerId(UUID customerId) {
        Optional<Cart>  cartByCustomer =   cartRepository.findByCustomerId(customerId);
        if (cartByCustomer.isEmpty()) throw new EntityNotFoundException("Cart isn't available");
        return cartByCustomer;
    }
}
