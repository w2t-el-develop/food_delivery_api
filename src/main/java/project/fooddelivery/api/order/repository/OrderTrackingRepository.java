package project.fooddelivery.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.order.entity.OrderTracking;

import java.util.UUID;

public interface OrderTrackingRepository extends JpaRepository<OrderTracking, UUID> {
}
