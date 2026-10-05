package project.fooddelivery.api.customer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.fooddelivery.api.customer.entity.Customer;
import project.fooddelivery.api.customer.entity.User;
import project.fooddelivery.api.customer.repository.CustomerRepository;

@Service 
@RequiredArgsConstructor 
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findByUserId(String userId) {
        return customerRepository.findByUser_UserId(userId);
                
    }
   

    
}
