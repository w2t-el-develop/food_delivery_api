package project.fooddelivery.api.cart.infrastructure.eventstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.fooddelivery.api.cart.infrastructure.eventstore.entity.CartEvent;

import java.util.List;
import java.util.UUID;

public interface CartEventStoreRepository extends JpaRepository<CartEvent, UUID> {

    List<CartEvent> findByCartEventAggregateIdOrderByCartEventAggregateVersionAsc(UUID cartEventAggregateId);

    @Query("""
            SELECT COALESCE(MAX(cartEvent.cartEventAggregateVersion), 0)
            FROM CartEvent cartEvent
            WHERE cartEvent.cartEventAggregateId = :aggregateId
            """)
    long findCurrentVersion(
            @Param("cartEventAggregateId") UUID cartEventAggregateId
    );
}
