package project.fooddelivery.api.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.fooddelivery.api.customer.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // userType is needed to build the user's role, and the JWT filter runs outside a transaction
    @EntityGraph(attributePaths = "userType")
    Optional<User> findByPhoneNumber(String phoneNumber);
}
