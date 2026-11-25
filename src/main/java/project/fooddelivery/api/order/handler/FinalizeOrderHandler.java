package project.fooddelivery.api.order.handler;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;
import project.fooddelivery.api.order.repository.OrderRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class FinalizeOrderHandler extends OrderHandler{

    private final OrderRepository orderRepository;

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        order.setAddressId(orderRequestDto.addressId());
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);
        return handleNext(orderRequestDto, order);
    }
}
