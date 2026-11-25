package project.fooddelivery.api.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.order.entity.Order;
import project.fooddelivery.api.order.entity.OrderTracking;
import project.fooddelivery.api.order.repository.OrderTrackingRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderTrackingService {

    private final OrderTrackingRepository orderTrackingRepository;

    public void trackOrder(Order order) {
        OrderTracking orderTracking = OrderTracking.trackOrder(order.getOrderId(), order.getOrderStatus().getOrderStatusId(),
                LocalDateTime.now(), order.getRestaurantId(), order.getCustomerId());
        orderTrackingRepository.save(orderTracking);
    }
}
