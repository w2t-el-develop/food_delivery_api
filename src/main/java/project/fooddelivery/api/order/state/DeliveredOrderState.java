package project.fooddelivery.api.order.state;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.order.service.OrderService;

@RequiredArgsConstructor
public class DeliveredOrderState implements OrderState {
    private final OrderService orderService;

    @Override
    public void processOrder() {

    }

    @Override
    public void shipOrder() {

    }

    @Override
    public void deliverOrder() {

    }

    @Override
    public void cancelOrder() {

    }
}
