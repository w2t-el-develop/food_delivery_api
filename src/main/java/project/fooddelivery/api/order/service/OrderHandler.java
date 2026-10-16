package project.fooddelivery.api.order.service;

import project.fooddelivery.api.order.dto.OrderRequestDto;

public interface OrderHandler {
    OrderHandler setNext(OrderHandler orderHandler);
    boolean handle(OrderRequestDto orderRequestDto);
}
