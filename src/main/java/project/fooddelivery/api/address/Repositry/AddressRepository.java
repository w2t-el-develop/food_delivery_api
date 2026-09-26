package project.fooddelivery.api.address.Repositry;

import com.example.food_Delivery_api_v3.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    boolean existsAddressByAddressIdAndCustomerId(UUID addressId, UUID customerId);

    @Modifying
    @Query(
            """
                        DELETE FROM Address ad 
                        WHERE ad.addressId = :addressId
                        AND ad.customerId = :customerId
                    """
    )
    Integer deleteByAddressIdAndCustomerId(
            @Param("addressId") UUID addressId,
            @Param("customerId") UUID customerId
    );
}
