package project.fooddelivery.api.order.entity;


import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id", nullable = false, updatable = false)
    private int orderStatusId;

    @Column(name = "order_status_code",  nullable = false, updatable = false, unique = true)
    private String orderStatusCode;

    @Column(name = "order_status_description")
    private String orderStatusDescription;

}
