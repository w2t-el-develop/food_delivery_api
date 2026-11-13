package project.fooddelivery.api.order.handler;

import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;

public class OrderTrackingHandler extends AbstractOrderHandler{
    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {

        return super.handle(orderRequestDto, order);
    }
}
