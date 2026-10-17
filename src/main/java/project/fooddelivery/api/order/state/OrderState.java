package project.fooddelivery.api.order.state;

public interface OrderState {
    void processOrder();
    void shipOrder();
    void deliverOrder();
    void cancelOrder();

}
