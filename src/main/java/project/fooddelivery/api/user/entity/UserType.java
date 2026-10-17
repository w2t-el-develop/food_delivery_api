package project.fooddelivery.api.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_type")
public class UserType {

    public static final String CUSTOMER = "CUSTOMER";

    @Id
    @ColumnDefault("uuidv7()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_type_id", nullable = false, updatable = false)
    private UUID userTypeId;

    @Column(name = "user_type_name", unique = true, nullable = false)
    private String userTypeName;

}
