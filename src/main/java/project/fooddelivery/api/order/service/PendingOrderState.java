package project.fooddelivery.api.order.service;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.order.exception.IllegalOrderStateException;

@RequiredArgsConstructor
public class PendingOrderState implements OrderState {

    private final OrderManagementService orderManagementService;

    @Override
    public void processOrder() {
        orderManagementService.changeOrderState(new ProcessingOrderState(orderManagementService));
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
        orderManagementService.changeOrderState(new CancelledOrderState(orderManagementService));
    }
}
