package project.fooddelivery.api.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.fooddelivery.api.customer.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByPhoneNumber(String phoneNumber);
}
