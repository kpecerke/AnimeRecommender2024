package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String SECRET_KEY = "secret"; // Tajný kľúč pre podpis JWT tokenov

    // Metóda na extrahovanie používateľského mena z JWT tokenu
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Metóda na extrahovanie dátumu expirácie z JWT tokenu
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generická metóda na extrahovanie konkrétneho claimu z JWT tokenu
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Metóda na extrahovanie všetkých claimov z JWT tokenu
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Metóda na kontrolu expirácie JWT tokenu
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Metóda na generovanie JWT tokenu na základe používateľského mena
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Metóda na vytvorenie JWT tokenu s určitými claimami a podpisom
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Platnosť tokenu: 10 hodín
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    // Metóda na validáciu JWT tokenu
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        // Kontrola, či používateľské meno z tokenu sa zhoduje s očakávaným menom a či token nevypršal
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
