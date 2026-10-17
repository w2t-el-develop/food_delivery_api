package project.fooddelivery.api.order.handler;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.Customer.entity.Customer;
import project.fooddelivery.api.Customer.service.CustomerService;
import project.fooddelivery.api.exceptionhandling.ResourceNotFoundException;
import project.fooddelivery.api.order.dto.OrderRequestDto;
import project.fooddelivery.api.order.entity.Order;

@RequiredArgsConstructor
public class CustomerExistenceCheckHandler extends AbstractOrderHandler {

    private final CustomerService customerService;

    @Override
    public Order handle(OrderRequestDto orderRequestDto, Order order) {
        Customer customer = customerService.getCustomerByCustomerId(orderRequestDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        order.setCustomerId(customer.getCustomerId());
        return super.handle(orderRequestDto, order);
    }
}
