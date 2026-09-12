package project.fooddelivery.api.menu;

import java.util.UUID;

public record MockMenuItem(
        UUID menuItemId,
        String name,
        Double price,
        boolean available
) {
}
