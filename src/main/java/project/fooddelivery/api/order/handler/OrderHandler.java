package project.fooddelivery.api.order.handler;

import org.springframework.stereotype.Component;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;

@Component
public abstract class OrderHandler {

    private OrderHandler next;


    public static OrderHandler ProcessOrder(OrderHandler first, OrderHandler... chain) {
        OrderHandler head = first;
        for (OrderHandler nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract Order handle(OrderRequestDto request, Order order);

    protected Order handleNext(OrderRequestDto request, Order order) {
        return next == null ? order : next.handle(request, order);
    }
}
