package project.fooddelivery.api.order.entity;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private UUID orderItemId;

    private UUID orderId;


}
