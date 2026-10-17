package project.fooddelivery.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import project.fooddelivery.api.auth.config.JwtProperties;
import project.fooddelivery.api.auth.config.SecurityConfig;
import project.fooddelivery.api.user.entity.User;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(user.getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.getAccessTokenTtl()))
                .claim(SecurityConfig.ROLES_CLAIM, List.of(user.getUserType().getUserTypeName()))
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
