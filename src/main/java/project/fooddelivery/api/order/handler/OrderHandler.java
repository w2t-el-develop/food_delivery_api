package project.fooddelivery.api.order.handler;

import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;

public interface OrderHandler {
    OrderHandler setNext(OrderHandler orderHandler);
    Order handle(OrderRequestDto orderRequestDto, Order order);
}
