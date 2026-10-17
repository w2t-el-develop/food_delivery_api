package project.fooddelivery.api.order.state;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.order.exception.IllegalOrderStateException;
import project.fooddelivery.api.order.service.OrderService;

@RequiredArgsConstructor
public class PendingOrderState implements OrderState {

    private final OrderService orderService;

    @Override
    public void processOrder() {
//        orderService.changeOrderState(new ProcessingOrderState(orderService));
    }

    @Override
    public void shipOrder() {

    }

    @Override
    public void deliverOrder() {
        throw new IllegalOrderStateException("can't deliver order at this moment");
    }

    @Override
    public void cancelOrder() {
//        orderService.changeOrderState(new CancelledOrderState(orderService));
    }
}
