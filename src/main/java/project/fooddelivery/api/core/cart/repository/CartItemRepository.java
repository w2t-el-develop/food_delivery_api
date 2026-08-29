package project.fooddelivery.api.core.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.fooddelivery.api.core.cart.dto.CartItemResponseDto;
import project.fooddelivery.api.core.cart.model.CartItem;

import java.util.*;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItemResponseDto> getCartItemsByCart_CartId(UUID cartId);

    Optional<CartItem> getCartItemByCart_cartIdAndMenuItemId(UUID cartItemId, UUID menuItemId);

    Optional<CartItem> getCartItemByCartItemIdAndCart_customerId(UUID cartItemId, UUID customerId);

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
            @Param("cartItemId") UUID cartItemId,
            @Param("customerIds") Collection<UUID> customerIds
    );




}
