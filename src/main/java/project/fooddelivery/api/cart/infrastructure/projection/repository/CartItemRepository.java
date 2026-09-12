package project.fooddelivery.api.cart.infrastructure.projection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.fooddelivery.api.cart.dto.response.CartItemResponseDto;
import project.fooddelivery.api.cart.infrastructure.projection.entity.CartItem;

import java.util.*;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItemResponseDto> getCartItemsByCart_CartId(UUID cartId);

    Optional<CartItem> getCartItemByCartItemIdAndCart_customerId(UUID cartItemId, UUID customerId);

    boolean existsCartItemByCartItemIdAndCart_customerId(UUID cartItemId, UUID customerId);

    @Modifying
    @Query("""
                DELETE FROM CartItem ci
                WHERE ci.cartItemId = :cartItemId
                  AND ci.cart.customerId = :customerId
            """)
    int deleteByCartItemIdAndCustomerId(
            @Param("cartItemId") UUID cartItemId,
            @Param("customerId") UUID customerId
    );

    @Modifying
    @Query("""
                DELETE FROM CartItem ci
                WHERE ci.cart.customerId = :customerId
                AND ci.cartItemId IN :cartItemIds
            """)
    int deleteByCartItemsIdAndCustomerId(
            @Param("customerId") UUID customerId,
            @Param("cartItemIds") Collection<UUID> cartItemIds
    );

    @Modifying
    @Query("""
                DELETE FROM CartItem ci
                WHERE ci.cart.customerId = :customerId
            """)
    void deleteAllByCustomerId(
            @Param("customerId") UUID customerId
    );

}
