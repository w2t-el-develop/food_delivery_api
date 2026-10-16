package project.fooddelivery.api.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.fooddelivery.api.menu.entity.MenuItem;

import java.util.List;
import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    Boolean existsMenuItemByMenuItemCode(String menuItemCode);
    Boolean existsMenuItemByMenuItemId(UUID menuItemId);

    @Modifying
    @Query(
            """
        UPDATE MenuItem mi SET mi.menuItemName = :menuItemName, mi.menuItemPrice = :menuItemPrice, mi.isAvailable = :isAvailable, mi.menuItemCode = :menuItemCode, mi.menuItemImageUrl = :menuItemImageUrl
        WHERE mi.menuItemId = :menuItemId
"""
    )
    Integer updateMenuItem(
            @Param("menuItemId") UUID menuItemId,
            @Param("menuItemName") String menuItemName,
            @Param("menuItemPrice") Double menuItemPrice,
            @Param("isAvailable") Boolean isAvailable,
            @Param("menuItemImageUrl") String menuItemImageUrl,
            @Param("menuItemCode") String menuItemCode
    );

    @Modifying
    @Query("""
        DELETE FROM MenuItem  mi WHERE mi.menuItemId = :menuItemID
        """)
    Integer deleteMenuItem(@Param("menuItemID") UUID menuItemID);

    @Query("""
        SELECT mi FROM MenuItem mi WHERE mi.menuId = :menuId
""")
    List<MenuItem> findAllMenuItemsByMenuId(@Param("menuId") UUID menuId);

    @Modifying
    @Query("""
        DELETE FROM MenuItem  mi WHERE mi.menuId = :menuId
        """)
    Integer deleteAllByMenuID(@Param("menuId") UUID menuId);
}
