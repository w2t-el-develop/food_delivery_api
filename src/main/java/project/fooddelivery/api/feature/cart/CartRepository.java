package project.fooddelivery.api.feature.cart;

import org.springframework.data.jpa.repository.JpaRepository;

interface CartRepository extends JpaRepository<String, Cart> {

}
