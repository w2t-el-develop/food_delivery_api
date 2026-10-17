package project.fooddelivery.api.order.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;
import project.fooddelivery.api.restaurant.exception.RestaurantAvailabilityException;
import project.fooddelivery.api.restaurant.service.RestaurantService;

@Component
@RequiredArgsConstructor
public class RestaurantAvailabilityCheckHandler extends AbstractOrderHandler{

    private final RestaurantService restaurantService;

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        if(restaurantService.isRestaurantOpen(orderRequestDto.restaurantId())){
            order.setRestaurantId(orderRequestDto.restaurantId());
        }else{
            throw new RestaurantAvailabilityException("restaurant is closed at current time");
        }
        return super.handle(orderRequestDto, order);
    }
}
