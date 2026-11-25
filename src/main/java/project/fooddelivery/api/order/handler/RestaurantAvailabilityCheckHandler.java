package project.fooddelivery.api.order.handler;

import lombok.RequiredArgsConstructor;

import project.fooddelivery.api.enums.ErrorMessage;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;
import project.fooddelivery.api.restaurant.exception.RestaurantAvailabilityException;
import project.fooddelivery.api.restaurant.service.RestaurantService;

@RequiredArgsConstructor
public class RestaurantAvailabilityCheckHandler extends OrderHandler{

    private final RestaurantService restaurantService;

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        if(restaurantService.isRestaurantOpen(orderRequestDto.restaurantId())){
            order.setRestaurantId(orderRequestDto.restaurantId());
        }else{
            throw new RestaurantAvailabilityException(ErrorMessage.RESTAURANT_CLOSED.getMessage());
        }
        return handleNext(orderRequestDto, order);
    }
}
