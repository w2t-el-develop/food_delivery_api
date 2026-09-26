package project.fooddelivery.api.address.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID addressId;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "building_number")
    private String buildingNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;


}
