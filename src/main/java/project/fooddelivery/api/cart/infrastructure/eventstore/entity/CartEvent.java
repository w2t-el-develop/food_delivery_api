package project.fooddelivery.api.cart.infrastructure.eventstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.time.Instant;
import java.util.UUID;



@Entity
@Table(
        name = "cart_event",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_cart_event_version",
                        columnNames = {
                                "aggregate_id",
                                "aggregate_version"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_cart_event_stream",
                        columnList = "aggregate_id, aggregate_version"
                )
        }
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartEvent {


    @Id
    @ColumnDefault("uuidv7()")
    @Column(name = "cart_event_id", nullable = false, updatable = false)
    private UUID CartEventId;

    @Column(name = "cart_event_aggregate_id", nullable = false, updatable = false)
    private UUID cartEventAggregateId;

    @Column(name = "cart_event_aggregate_version", nullable = false, updatable = false)
    private Long cartEventAggregateVersion;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "event_type_id",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_cart_event_event_type")
    )
    private CartEventType cartEventType;

    @Column(
            name = "event_schema_version",
            nullable = false,
            updatable = false
    )
    private int eventSchemaVersion;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(
            name = "payload",
            nullable = false,
            updatable = false,
            columnDefinition = "jsonb"
    )
    private JsonNode payload;

    @Column(name = "occurred_at", nullable = false, updatable = false)
    private Instant occurredAt;
}
