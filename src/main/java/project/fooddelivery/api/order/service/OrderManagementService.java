package project.fooddelivery.api.order.service;

import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderManagementService {

    private OrderState orderState;

    public  OrderManagementService() {
        this.orderState = new PendingOrderState(this);
    }

    public void changeOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    private void processOrder(){
        orderState.processOrder();
    }

    public void deliverOrder(){
        orderState.deliverOrder();
    }

    public void cancelOrder(){
        orderState.cancelOrder();
    }
}
