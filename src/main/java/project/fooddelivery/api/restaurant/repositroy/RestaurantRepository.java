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

    @Modifying
    @Query("""
        UPDATE Restaurant r SET r.restaurantName = :restaurantName, r.restaurantDescription = :restaurantDescription 
        WHERE r.restaurantId = :restaurantId
""")
    Integer updateRestaurant(
            @Param("restaurantId") UUID restaurantId,
            @Param("restaurantName") String restaurantName,
            @Param("restaurantDescription") String restaurantDescription
    );

    @Modifying
    @Query(
            """ 
        DELETE  From Restaurant r WHERE r.restaurantId = :restaurantId 
"""
    )
    Integer bulkDeleteRestaurant(@Param("restaurantId") UUID restaurantId);

}
