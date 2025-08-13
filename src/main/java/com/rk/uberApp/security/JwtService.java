package com.rk.uberApp.security;


import com.rk.uberApp.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {


    @Value("${jwt.secretKey}")
    private String SECRETE_KEY;

    public String generateJwtAccessToken(User user) {

        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().toString())
                .signWith(getSecreteKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .signWith(getSecreteKey())
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 6))
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecreteKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }

    public SecretKey getSecreteKey() {
        return Keys.hmacShaKeyFor(SECRETE_KEY.getBytes(StandardCharsets.UTF_8));
    }


}
