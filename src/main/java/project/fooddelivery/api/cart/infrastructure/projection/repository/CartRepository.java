package project.fooddelivery.api.cart.infrastructure.projection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.cart.infrastructure.projection.entity.Cart;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> getCartByCustomerId(UUID customerId);
    boolean existsByCustomerId(UUID customerId);
}

