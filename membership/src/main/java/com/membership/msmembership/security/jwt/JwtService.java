package com.membership.msmembership.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JwtService {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final Duration ttl;

    public JwtService(PrivateKey privateKey, PublicKey publicKey, Duration ttl) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.ttl = ttl;
    }

    public String generateToken(Long userId, String email, String rolesCsv) {
        Instant now = Instant.now();
        Instant exp = now.plus(ttl);

        List<String> roles = Arrays.stream(rolesCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .claim("roles", roles)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public long expiresInSeconds() {
        return ttl.toSeconds();
    }

    public JwtClaims parseAndValidate(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userId = claims.getSubject();
        String email = claims.get("email", String.class);

        @SuppressWarnings("unchecked")
        List<String> roles = claims.get("roles", List.class);

        return new JwtClaims(userId, email, roles);
    }

    public record JwtClaims(String userId, String email, List<String> roles) {}
}
