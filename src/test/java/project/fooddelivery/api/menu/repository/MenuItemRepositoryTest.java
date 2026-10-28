package project.fooddelivery.api.menu.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import project.fooddelivery.api.menu.entity.MenuItem;

import java.math.BigDecimal;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MenuItemRepositoryTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    public void MenuItemRepository_Save_ReturnSavedMenuItem() {

        //Arrange
        MenuItem menuItem = MenuItem.buildMenuItem("Koshary", BigDecimal.valueOf(70.50), true);

        //Act
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        //Assert
        Assertions.assertThat(savedMenuItem).isNotNull();
        Assertions.assertThat(savedMenuItem.getMenuItemId()).isNotNull();
    }


    @Test
    public void MenuItemRepository_GetAll_ReturnMoreThanOneMenuItem() {

        MenuItem menuItem1 = MenuItem.buildMenuItem("pasta", BigDecimal.valueOf(20.50), true);
        MenuItem menuItem2 = MenuItem.buildMenuItem("Kofta", BigDecimal.valueOf(50.99), true);
        menuItemRepository.saveAll(List.of(menuItem1, menuItem2));

        List<MenuItem> menuItems = menuItemRepository.findAll();

        Assertions.assertThat(menuItems).isNotNull();
        Assertions.assertThat(menuItems.size()).isEqualTo(2);

    }


}
