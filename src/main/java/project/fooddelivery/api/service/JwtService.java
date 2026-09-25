package project.fooddelivery.api.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
    private String secretKey;

    public String generateToken(String username, String userId) {
      Map<String, Object> claims = new HashMap<>();
      claims.put("user_id", userId);
      return Jwts.builder()
              .claims(claims)
              .subject(username)
              .issuedAt(new Date(System.currentTimeMillis()))
              .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
              .signWith(getKey())
              .compact();
    }

    private Key getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public JwtService(){
        try {
            KeyGenerator keyGenerator =KeyGenerator.getInstance("HmacSHA256");
            SecretKey sKey =keyGenerator.generateKey();
            secretKey=Base64.getEncoder().encodeToString(sKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
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
       return extractClaims(token, Claims::getExpiration);
    }

    public String extractPhoneNumber(String token) {
       return extractClaims(token, Claims::getSubject);
    }

    public String extractUserId(String token) {
       return extractClaims(token, claims -> claims.get("user_id", String.class));
    }

    private <T>T extractClaims(String token, Function<Claims,T> claimsResolver) {
       final Claims claims = extractAllClaims(token);
       return claimsResolver.apply(claims);
        
       
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload();
    }
    
}
