package project.fooddelivery.api.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.fooddelivery.api.customer.entity.UserType;

public interface UserTypeRepository  extends JpaRepository<UserType, String>{

    UserType findByUserTypeName(String userTypeName);
    

    
}
