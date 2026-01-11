package com.login.authenication.Security;

import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET =
        "my-super-secret-key-my-super-secret-key"; // >= 32 chars
    private static final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    private final SecretKey key =
        Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ Generate JWT with email + role
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)               // email
                .claim("role", role)          // role
                .issuedAt(new Date())
                .expiration(
                    new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .signWith(key)
                .compact();
    }

    // ✅ Extract email (subject)
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // ✅ Extract role
    public String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }
}
