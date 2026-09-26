package project.fooddelivery.api.customer.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.customer.entity.Customer;

@Repository 
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
}
