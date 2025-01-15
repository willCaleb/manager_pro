package com.project.pro.config.security;

import com.project.pro.enums.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt-secret}")
    private String jwtSecret;
    private int jwtExpirationInMs = 604800000; // 7 dias

    public String generateToken(Authentication authentication, Role role, String userType) {
        User userPrincipal = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("userType", userType)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .setAudience(role.getKey())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getTypeClaim(String token, String claimKey) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .get(claimKey, String.class);
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getAudienceFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getAudience();
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            return false;
        }
    }
}
