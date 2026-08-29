package project.fooddelivery.api.core.menu;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
class MockMenuItemRepository {

    private final Map<UUID, MockMenuItem> menuItems = Map.of(
            UUID.fromString("0190f001-1111-7000-8000-000000000001"),
            new MockMenuItem(
                    UUID.fromString("0190f001-1111-7000-8000-000000000001"),
                    "Chicken Burger",
                    120.0,
                    true
            ),

            UUID.fromString("0190f001-1111-7000-8000-000000000002"),
            new MockMenuItem(
                    UUID.fromString("0190f001-1111-7000-8000-000000000002"),
                    "Beef Burger",
                    150.0,
                    true
            ),

            UUID.fromString("0190f001-1111-7000-8000-000000000003"),
            new MockMenuItem(
                    UUID.fromString("0190f001-1111-7000-8000-000000000003"),
                    "Margherita Pizza",
                    180.0,
                    true
            ),

            UUID.fromString("0190f001-1111-7000-8000-000000000004"),
            new MockMenuItem(
                    UUID.fromString("0190f001-1111-7000-8000-000000000004"),
                    "French Fries",
                    55.0,
                    true
            ),

            UUID.fromString("0190f001-1111-7000-8000-000000000005"),
            new MockMenuItem(
                    UUID.fromString("0190f001-1111-7000-8000-000000000005"),
                    "Grilled Chicken",
                    220.0,
                    false
            )
    );

    public Optional<MockMenuItem> getById(UUID menuItemId) {
        return Optional.ofNullable(menuItems.get(menuItemId));
    }

}

record MockMenuItem(
        UUID menuItemId,
        String name,
        Double price,
        boolean available
) {
}
