package project.fooddelivery.api.order.service;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.order.exception.IllegalOrderStateException;
import project.fooddelivery.api.order.exception.OrderAlreadyCanceledException;

@RequiredArgsConstructor
public class CancelledOrderState implements OrderState {
    private final OrderManagementService orderManagementService;

    @Override
    public void processOrder() {
        throw new IllegalOrderStateException("can't process order at this moment");
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
        throw new OrderAlreadyCanceledException("order already cancelled");
    }
}
