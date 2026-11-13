package project.fooddelivery.api.order.handler;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.cart.service.CartService;
import project.fooddelivery.api.enums.ErrorMessage;
import project.fooddelivery.api.exception.ResourceNotFoundException;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;

@RequiredArgsConstructor
public class CartExistenceCheckHandler extends AbstractOrderHandler{

    private final CartService cartService;

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        cartService.getCartByCustomerId(orderRequestDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.CART_NOT_FOUND.getMessage()));
        return super.handle(orderRequestDto, order);
    }
}
