package project.fooddelivery.api.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final SecretKey signingKey;

    public JwtService(@Value("${jwt.secret}") String base64Secret) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    }

    public String generateToken(String phoneNumber, String userId, String customerId, String userType) {
      Map<String, Object> claims = new HashMap<>();
      claims.put("user_id", userId);
            claims.put("customer_id", customerId);
      claims.put("user_type", userType);
      return Jwts.builder()
              .claims(claims)
              .subject(phoneNumber)
              .issuedAt(new Date(System.currentTimeMillis()))
              .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
              .signWith(signingKey)
              .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
      final String phoneNumber = extractPhoneNumber(token);
      final String userId = extractUserId(token);
      return (phoneNumber.equals(userDetails.getUsername()) && !isTokenExpired(token) && userId != null && !userId.isBlank());
    }

    private boolean isTokenExpired(String token) {
       return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
       return extractAllClaims(token).getExpiration();
    }

    public String extractPhoneNumber(String token) {
       return extractAllClaims(token).getSubject();
    }

    public String extractUserId(String token) {
       return extractClaims(token, claims -> claims.get("user_id", String.class));
    }

     public String extractCustomerId(String token) {
         return extractClaims(token, claims -> claims.get("customer_id", String.class));
     }

    private <T>T extractClaims(String token, Function<Claims,T> claimsResolver) {
       final Claims claims = extractAllClaims(token);
       return claimsResolver.apply(claims);
        
       
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }
    
}
