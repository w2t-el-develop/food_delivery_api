package project.fooddelivery.api.order.service;

import org.springframework.stereotype.Component;
import project.fooddelivery.api.order.dto.OrderRequestDto;

@Component
public class AbstractOrderHandler implements OrderHandler {

    private OrderHandler nextOrderHandler;

    @Override
    public OrderHandler setNext(OrderHandler orderHandler) {
        this.nextOrderHandler = orderHandler;
        return orderHandler;
    }

    @Override
    public boolean handle(OrderRequestDto orderRequestDto) {
        if(nextOrderHandler != null) {
            return nextOrderHandler.handle(orderRequestDto);
        }
        return true;
    }
}
