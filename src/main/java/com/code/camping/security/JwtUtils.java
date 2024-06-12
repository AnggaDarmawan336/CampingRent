package com.code.camping.security;

import com.code.camping.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    private final String jwtSignatureSecret = "java-incubation-25-final-project-team-2";
    private final int jwtExpirationInMs = 1000 * 60 * 60 * 24;

    public SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSignatureSecret.getBytes());
    }

    public String generateAccessToken(User user) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        return Jwts.builder().subject(user.getId())
                .issuedAt(currentDate).expiration(expirationDate)
                .signWith(getSigningKey()).compact();
    }

    public Claims decodeAccessToken(String accessToken){
        return Jwts.parser().verifyWith(getSigningKey())
                .build().parseSignedClaims(accessToken).getPayload();
    }
}
