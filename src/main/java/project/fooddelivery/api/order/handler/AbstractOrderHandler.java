package project.fooddelivery.api.order.handler;

import org.springframework.stereotype.Component;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;

@Component
public class AbstractOrderHandler implements OrderHandler {

    private OrderHandler nextOrderHandler;

    @Override
    public OrderHandler setNext(OrderHandler orderHandler) {
        this.nextOrderHandler = orderHandler;
        return orderHandler;
    }


    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        if(nextOrderHandler != null) {
            return nextOrderHandler.handle(orderRequestDto, order);
        }
        return order;
    }

}
