package project.fooddelivery.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.user.entity.UserType;

import java.util.Optional;
import java.util.UUID;

public interface UserTypeRepository extends JpaRepository<UserType, UUID> {

    Optional<UserType> findByUserTypeName(String userTypeName);
}
