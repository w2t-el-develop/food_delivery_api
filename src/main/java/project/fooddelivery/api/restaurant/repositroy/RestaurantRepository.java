package project.fooddelivery.api.restaurant.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.fooddelivery.api.restaurant.entity.Restaurant;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {


    boolean existsRestaurantByRestaurantCode(String restaurantCode);

    boolean existsRestaurantByRestaurantId(UUID restaurantId);


}
