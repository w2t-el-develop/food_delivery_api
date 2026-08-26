package project.fooddelivery.api.Cart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.Cart.model.Cart;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCustomerId(UUID customerId);
}
