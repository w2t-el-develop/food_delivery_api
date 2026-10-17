package project.fooddelivery.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.order.entity.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
