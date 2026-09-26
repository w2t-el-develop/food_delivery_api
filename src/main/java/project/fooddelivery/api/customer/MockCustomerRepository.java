package project.fooddelivery.api.customer;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class MockCustomerRepository {
    public final Map<UUID, MockCustomer> customers = Map.of(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
            new MockCustomer(
                    UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                    UUID.fromString("7c9e6679-7425-40de-944b-e07fc1f90ae7")
            ),
            UUID.fromString("6ba7b810-9dad-41d1-80b4-00c04fd430c8"),
            new MockCustomer(
                    UUID.fromString("6ba7b810-9dad-41d1-80b4-00c04fd430c8"),
                    UUID.fromString("3f2504e0-4f89-41d3-9a0c-0305e82c3301")
            ),
            UUID.fromString("1b4e28ba-2fa1-41ae-9c3f-8b7f4e5d6a21"),
            new MockCustomer(
                    UUID.fromString("1b4e28ba-2fa1-41ae-9c3f-8b7f4e5d6a21"),
                    UUID.fromString("9f8b7c6d-5e4f-43a2-b1c0-d9e8f7a6b5c4")
            )
    );

    public boolean isExistCustomer(UUID customerId) {
        return customers.containsKey(customerId);
    }
}
