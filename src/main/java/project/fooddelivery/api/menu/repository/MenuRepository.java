package project.fooddelivery.api.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.fooddelivery.api.menu.entity.Menu;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    Optional<Menu> getMenuByMenuId(UUID menuId);

    @Query("""
            select case when count(menu) > 0 then true else false end
            from Menu menu where menu.menuId = :menuId
            """)
    boolean isMenuExistsById(UUID menuId);
}
