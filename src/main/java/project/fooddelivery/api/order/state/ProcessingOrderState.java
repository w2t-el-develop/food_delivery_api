package project.fooddelivery.api.order.state;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.order.exception.OrderAlreadyProcessingException;
import project.fooddelivery.api.order.service.OrderService;

@RequiredArgsConstructor
public class ProcessingOrderState implements OrderState {
    private final OrderService orderService;

    @Override
    public void processOrder() {
        throw new OrderAlreadyProcessingException("order already processing");
    }

    @Override
    public void shipOrder() {

    }


    @Override
    public void deliverOrder() {
//        orderService.changeOrderState(new DeliveredOrderState(orderService));
    }

    @Override
    public void cancelOrder() {

    }
}
