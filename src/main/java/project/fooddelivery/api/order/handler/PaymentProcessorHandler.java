package project.fooddelivery.api.order.handler;

import lombok.extern.slf4j.Slf4j;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;

@Slf4j
public class PaymentProcessorHandler extends OrderHandler{

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        log.info("Processing payment......");
        log.info("Payment success");
        return handleNext(orderRequestDto, order);
    }
}
