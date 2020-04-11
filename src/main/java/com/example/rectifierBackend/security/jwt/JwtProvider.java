package com.example.rectifierBackend.security.jwt;

import com.example.rectifierBackend.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value(value = "${JWT_SECRET}")
    private String jwtSecret;

    private int jwtExpiration = 3600;

    public String generateJwtToken(Authentication authentication) {
        User userPrinciple = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature -> Message: {} " + e);
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token -> Message: {}" + e);
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token -> Message: {}" + e);
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token -> Message: {}" + e);
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty -> Message: {}" + e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

}
