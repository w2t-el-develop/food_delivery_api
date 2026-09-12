package project.fooddelivery.api.order.service;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.order.exception.OrderAlreadyProcessingException;

@RequiredArgsConstructor
public class ProcessingOrderState implements OrderState{
    private final OrderManagementService orderManagementService;

    @Override
    public void processOrder() {
        throw new OrderAlreadyProcessingException("order already processing");
    }

    @Override
    public void shipOrder() {

    }


    @Override
    public void deliverOrder() {
        orderManagementService.changeOrderState(new DeliveredOrderState(orderManagementService));
    }

    @Override
    public void cancelOrder() {

    }
}
