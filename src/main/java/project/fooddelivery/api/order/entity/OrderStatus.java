package project.fooddelivery.api.order.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @ColumnDefault("uuidv7()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_status_id", nullable = false, updatable = false)
    private UUID orderStatusId;

    @Column(name = "order_status_code",  nullable = false, updatable = false, unique = true)
    private String orderStatusCode;

    @Column(name = "order_status_description")
    private String orderStatusDescription;

}
