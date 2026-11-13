package project.fooddelivery.api.order.handler;

import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;

import java.math.BigDecimal;

public class TotalPriceCalculationHandler extends AbstractOrderHandler{

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        BigDecimal totalOrderPrice= order.getOrderItems().stream()
                .map(order)
        return super.handle(orderRequestDto, order);
    }
}
