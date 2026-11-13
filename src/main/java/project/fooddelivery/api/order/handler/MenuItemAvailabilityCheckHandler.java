package project.fooddelivery.api.order.handler;


import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.menu.entity.MenuItem;
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
            MenuItem menuItem = menuItemService.getMenuItemByIdIfMenuItemIsAvailable(orderItemRequestDto.menuItemId());
            OrderItem newOrderItem = OrderItem.buildOrderItem(
                    menuItem.getMenuItemId(),
                    orderItemRequestDto.orderItemQuantity(),
                    menuItem.getMenuItemPrice());
            newOrderItems.add(newOrderItem);
        }
        order.addItems(newOrderItems);
        return super.handle(orderRequestDto, order);
    }
}
