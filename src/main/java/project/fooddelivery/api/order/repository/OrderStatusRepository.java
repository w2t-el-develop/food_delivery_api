package project.fooddelivery.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.fooddelivery.api.order.entity.OrderStatus;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {

    @Query("select order_status from OrderStatus order_status where order_status.orderStatusCode = 'PENDING' ")
    Optional<OrderStatus> getPendingOrderStatus();
}
