package project.fooddelivery.api.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.Customer.service.CustomerService;
import project.fooddelivery.api.cart.service.CartService;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;
import project.fooddelivery.api.order.handler.CartExistenceCheckHandler;
import project.fooddelivery.api.order.handler.CustomerExistenceCheckHandler;
import project.fooddelivery.api.order.handler.OrderHandler;
import project.fooddelivery.api.order.handler.RestaurantAvailabilityCheckHandler;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerService customerService;
    private final CartService cartService;

    public Order placeOrder(OrderRequestDto orderRequestDto){
        return new Order();
    }

    private void initializeOrderChainHandlers(){
        OrderHandler customerExistenceCheckHandler = new CustomerExistenceCheckHandler(customerService);
        OrderHandler cartExistenceCheckHandler = new CartExistenceCheckHandler(cartService);
        OrderHandler restaurantAvailabilityCheckHandler = new RestaurantAvailabilityCheckHandler();
    }

}
