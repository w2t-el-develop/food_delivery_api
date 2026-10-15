package project.fooddelivery.api.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import project.fooddelivery.api.user.entity.User;

import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID restaurantId;

    @Column(name = "restaurant_code",nullable = false, unique = true)
    private String restaurantCode;

    @Column(name  = "restaurant_name")
    private String restaurantName;

    @Column(name = "restaurant_description")
    private String restaurantDescription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
