package project.fooddelivery.api.grouprole.repository;

import project.fooddelivery.api.grouprole.entity.role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<role, String> {
}