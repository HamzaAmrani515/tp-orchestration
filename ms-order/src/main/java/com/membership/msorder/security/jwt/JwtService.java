package com.membership.msorder.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class JwtService {

    private final PublicKey publicKey;

    public JwtService() {
        this.publicKey = loadPublicKey("keys/public_key.pem");
    }

    public Jws<Claims> parseAndValidate(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
    }

    private static PublicKey loadPublicKey(String classpathLocation) {
        try {
            String pem = new String(new ClassPathResource(classpathLocation).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            String content = pem
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] decoded = Base64.getDecoder().decode(content);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load RSA public key from classpath: " + classpathLocation, e);
        }
    }
}
