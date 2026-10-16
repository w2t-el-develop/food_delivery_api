package project.fooddelivery.api.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.fooddelivery.api.menu.entity.Menu;

import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    boolean existsMenuByMenuCode(String menuCode);
    boolean existsMenuByMenuId(UUID menuId);
    @Modifying
    @Query("""
        UPDATE Menu m SET m.menuCategory = :menuCategory, m.menuCode = :menuCode
        WHERE m.menuId = :menuId
""")
    Integer updateMenuById(@Param("menuId") UUID menuId, @Param("menuCategory") String menuCategory, @Param("menuCode") String menuCode);

}
