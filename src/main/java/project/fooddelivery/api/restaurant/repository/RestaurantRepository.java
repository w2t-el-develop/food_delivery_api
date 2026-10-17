package project.fooddelivery.api.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.restaurant.entity.Restaurant;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    Optional<Restaurant> getRestaurantByRestaurantId(UUID restaurantId);
}
