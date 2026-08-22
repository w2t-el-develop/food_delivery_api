package project.fooddelivery.api.feature.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CartRepository extends JpaRepository<Cart, UUID> {

}
