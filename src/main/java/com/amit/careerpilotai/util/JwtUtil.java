package com.amit.careerpilotai.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY =
            "CareerPilotAISecretKeyForJWTAuthentication2026SpringBoot";

    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public String extractEmail(String token) {

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);

        return claims.getBody().getSubject();
    }

    public boolean validateToken(String token, String email) {

        return extractEmail(token).equals(email);
    }
}