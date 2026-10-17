package project.fooddelivery.api.order.service;

public interface OrderState {
    void processOrder();
    void shipOrder();
    void deliverOrder();
    void cancelOrder();

}
