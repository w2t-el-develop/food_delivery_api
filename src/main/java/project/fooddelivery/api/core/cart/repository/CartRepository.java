package project.fooddelivery.api.core.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.core.cart.model.Cart;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> getCartByCustomerId(UUID customerId);
}

