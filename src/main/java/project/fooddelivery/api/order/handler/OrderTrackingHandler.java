package project.fooddelivery.api.order.handler;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;
import project.fooddelivery.api.order.service.OrderTrackingService;


@RequiredArgsConstructor
public class OrderTrackingHandler extends OrderHandler{

    private final OrderTrackingService orderTrackingService;

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        orderTrackingService.trackOrder(order);
        return handleNext(orderRequestDto, order);
    }
}
