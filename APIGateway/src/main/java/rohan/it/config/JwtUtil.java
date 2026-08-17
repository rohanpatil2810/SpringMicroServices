package rohan.it.config;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // MUST be identical to the secret in UserLoginMicroservice
    private static final String SECRET = "MySecretKeyMySecretKeyMySecretKey123456";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);   // will throw if invalid/expired
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}