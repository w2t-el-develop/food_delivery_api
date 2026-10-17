package project.fooddelivery.api.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @ColumnDefault("uuidv7()")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "refresh_token_id", nullable = false, updatable = false)
    private UUID refreshTokenId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    // SHA-256 of the raw token, the raw value is only ever sent to the client
    @Column(name = "token_hash", unique = true, nullable = false, updatable = false, length = 64)
    private String tokenHash;

    @Column(name = "expires_at", nullable = false, updatable = false)
    private Instant expiresAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

}
