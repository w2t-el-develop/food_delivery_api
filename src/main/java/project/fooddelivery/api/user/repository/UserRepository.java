package project.fooddelivery.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
}
