package project.fooddelivery.api.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.Customer.service.CustomerService;
import project.fooddelivery.api.cart.service.CartService;
import project.fooddelivery.api.exception.ResourceNotFoundException;
import project.fooddelivery.api.menu.service.MenuItemService;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;
import project.fooddelivery.api.order.entity.OrderStatus;
import project.fooddelivery.api.order.handler.*;
import project.fooddelivery.api.order.repository.OrderRepository;
import project.fooddelivery.api.order.repository.OrderStatusRepository;
import project.fooddelivery.api.restaurant.service.RestaurantService;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerService customerService;
    private final CartService cartService;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final OrderRepository orderRepository;
    private final OrderTrackingService orderTrackingService;
    private final OrderStatusRepository orderStatusRepository;

    public Order placeOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setOrderStatus(getPendingOrderStatus());
        OrderHandler orderHandler = OrderHandler.ProcessOrder(new CustomerExistenceCheckHandler(customerService),
                new CartExistenceCheckHandler(cartService),
                new RestaurantAvailabilityCheckHandler(restaurantService),
                new MenuItemAvailabilityCheckHandler(menuItemService),
                new TotalPriceCalculationHandler(),
                new PaymentProcessorHandler(),
                new FinalizeOrderHandler(orderRepository),
                new OrderTrackingHandler(orderTrackingService));
        return orderHandler.handle(orderRequestDto, order);
    }

    OrderStatus getPendingOrderStatus(){
        return orderStatusRepository.getPendingOrderStatus()
                .orElseThrow(()-> new ResourceNotFoundException("Order Status not found"));
    }
}
