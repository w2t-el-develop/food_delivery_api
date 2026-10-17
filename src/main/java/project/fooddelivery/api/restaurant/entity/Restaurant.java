package project.fooddelivery.api.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @ColumnDefault("uuidv7()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "restaurant_id", nullable = false, updatable = false)
    private UUID restaurantId;

    @Column(name = "restaurant_code", nullable = false, length = 10, unique = true)
    private String restaurantCode;

    @Column(name = "restaurant_name", nullable = false, length = 100)
    private String restaurantName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_status_id", nullable = false)
    private RestaurantStatus restaurant_status_id;

    @Column(name = "restaurant_opening_time", nullable = false)
    private LocalTime openingTime;

    @Column(name = "restaurant_closing_time", nullable = false)
    private LocalTime closingTime;

    public boolean isOpen(){
        ZoneId restaurantZone = ZoneId.of("Africa/Cairo");
        Clock clock = Clock.system(restaurantZone);
        LocalTime localTime = LocalTime.now(clock);
        if (closingTime.isAfter(openingTime)) {
            return !localTime.isBefore(openingTime) && localTime.isBefore(closingTime);
        }
        return !localTime.isBefore(openingTime) || localTime.isBefore(closingTime);
    }

}
