package project.fooddelivery.api.cart.infrastructure.eventstore.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(
        name = "cart_event_type",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cart_event_type_code",
                        columnNames = "code"
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartEventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_event_type_id")
    private UUID CartEventTypeId;

    @Column(
            name = "code",
            nullable = false,
            unique = true,
            updatable = false,
            length = 80
    )
    private String eventTypeCode;

    @Column(name = "event_Type_name", nullable = false, length = 150)
    private String eventTypeName;

    @Column(name = "event_Type_description", length = 500)
    private String eventTypeDescription;
}
