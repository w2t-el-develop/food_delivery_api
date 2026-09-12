package project.fooddelivery.api.order.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveredOrderState implements OrderState {
    private final OrderManagementService orderManagementService;

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
