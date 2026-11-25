package project.fooddelivery.api.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fooddelivery.api.menu.entity.MenuItemImage;

import java.util.UUID;

public interface MenuItemImageRepository extends JpaRepository<MenuItemImage, UUID> {
}
