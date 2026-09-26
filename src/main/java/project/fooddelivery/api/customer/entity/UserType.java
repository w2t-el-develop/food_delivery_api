package project.fooddelivery.api.customer.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "user_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_type_id", nullable = false, updatable = false)
    private String userTypeId;

    @Column(name = "user_type_name")
    private String userTypeName;

    @OneToMany(mappedBy = "userType", fetch = FetchType.LAZY)
    private List<User> users;

    public UserType(String userTypeName) {
        this.userTypeName = userTypeName;
    }


}
