package project.fooddelivery.api.order.handler;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.cart.entity.Cart;
import project.fooddelivery.api.cart.service.CartService;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;

@RequiredArgsConstructor
public class CartExistenceCheckHandler extends AbstractOrderHandler{

    private final CartService cartService;

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        cartService.getCartByCustomerId(orderRequestDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart Not found"));
        return super.handle(orderRequestDto, order);
    }
}
