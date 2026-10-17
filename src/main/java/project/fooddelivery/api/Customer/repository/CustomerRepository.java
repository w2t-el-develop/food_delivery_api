package project.fooddelivery.api.Customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.Customer.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> getCustomerByCustomerId(UUID customerId);
}
