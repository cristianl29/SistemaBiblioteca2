package com.cristianlopera.biblioteca.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // NOTE: In production, store this key securely and load from config
    private final Key key = Keys.hmacShaKeyFor("MiClaveSecretaMuyLargaParaJWTMiClaveSecretaMuyLarga".getBytes());
    private final long expirationMs = 1000L * 60 * 60 * 4; // 4 hours

    public String generateToken(String username){
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMs))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().getSubject();
        }catch(Exception e){
            return null;
        }
    }
}
