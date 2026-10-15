package project.fooddelivery.api.user.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.fooddelivery.api.user.entity.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
//    @Modifying
//    // in query you use Entity's name not table's name
//    @Query("""
//        DELETE FROM User u WHERE u.userId = :userId
//""")
//    Integer deleteUserByUserId(@Param("userId") UUID userID);
//
//    // update the data of user
//    @Modifying
//    @Query(
//            """
//        UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.phoneNumber = :phoneNumber, u.userPassword = :userPassword
//        WHERE u.userId = :userId
//"""
//    )
//    Integer updateUserByUserID(
//            @Param("userId")  UUID userId,
//            @Param("firstName") String firstName,
//            @Param("lastName") String lastName,
//            @Param("phoneNumber") String phoneNumber,
//            @Param("userPassword") String userPassword
//    );
}
