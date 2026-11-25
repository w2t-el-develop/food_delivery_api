package project.fooddelivery.api.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_tracking")
public class OrderTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_tracking_id")
    private UUID orderTrackingId;

    @Column(name = "order_id", nullable = false, updatable = false)
    private UUID orderId;

    @Column(name = "order_status_id", nullable = false, updatable = false)
    private int orderStatusId;

    @Column(name = "order_tracking_date", nullable = false, updatable = false)
    private LocalDateTime orderTrackingDate;

    @Column(name = "restaurant_id", nullable = false, updatable = false)
    private UUID restaurantId;

    @Column(name = "customer_id", nullable = false, updatable = false)
    private UUID customerId;

    public static OrderTracking trackOrder(UUID orderId, int orderStatusId,
                                           LocalDateTime orderTrackingDate, UUID restaurantId, UUID customerId){
        return OrderTracking.builder()
                .orderId(orderId)
                .orderStatusId(orderStatusId)
                .orderTrackingDate(orderTrackingDate)
                .restaurantId(restaurantId)
                .customerId(customerId)
                .build();
    }
}
