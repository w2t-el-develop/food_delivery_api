package project.fooddelivery.api.order.handler;

import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;
import project.fooddelivery.api.order.entity.OrderItem;

import java.math.BigDecimal;

public class TotalPriceCalculationHandler extends AbstractOrderHandler{

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        BigDecimal totalOrderPrice = order.getOrderItems().stream()
                .map(OrderItem::getOrderItemTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setOrderTotalPrice(totalOrderPrice);
        return super.handle(orderRequestDto, order);
    }
}
