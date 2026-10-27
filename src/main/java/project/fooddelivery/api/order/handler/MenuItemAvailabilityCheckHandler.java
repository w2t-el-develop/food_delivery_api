package project.fooddelivery.api.order.handler;


import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.menu.service.MenuItemService;
import project.fooddelivery.api.order.dto.OrderItemRequestDto;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;
import project.fooddelivery.api.order.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class MenuItemAvailabilityCheckHandler extends AbstractOrderHandler {

    private final MenuItemService menuItemService;

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        List<OrderItem> newOrderItems = new ArrayList<>();
        for(OrderItemRequestDto orderItemRequestDto : orderRequestDto.orderItems()){
            OrderItem newOrderItem = new OrderItem();
            menuItemService.isMenuItemAvailable(orderItemRequestDto.menuItemId());
            newOrderItem.setMenuItemId(orderItemRequestDto.menuItemId());
            newOrderItems.add(newOrderItem);
        }
        order.addItems(newOrderItems);
        return super.handle(orderRequestDto, order);
    }
}
