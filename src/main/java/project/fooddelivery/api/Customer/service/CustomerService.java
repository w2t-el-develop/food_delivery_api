package project.fooddelivery.api.Customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.Customer.entity.Customer;
import project.fooddelivery.api.Customer.repository.CustomerRepository;


import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Optional<Customer> getCustomerByCustomerId(UUID customerId){
        return customerRepository.getCustomerByCustomerId(customerId);
    }

}
