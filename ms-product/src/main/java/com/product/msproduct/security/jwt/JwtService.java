package com.product.msproduct.security.jwt;

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
import java.util.List;

@Service
public class JwtService {

    private final PublicKey publicKey;

    public JwtService() {
        this.publicKey = loadPublicKey("keys/public_key.pem");
    }

    public JwtUser parseAndValidate(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jws.getBody();

            Long userId = Long.valueOf(claims.getSubject());
            String email = claims.get("email", String.class);

            Object rolesObj = claims.get("roles");
            List<String> roles = (rolesObj instanceof List<?> list)
                    ? list.stream().map(String::valueOf).toList()
                    : List.of();

            return new JwtUser(userId, email, roles);

        } catch (Exception e) {
            throw new JwtValidationException("Invalid token", e);
        }
    }

    private PublicKey loadPublicKey(String classpathLocation) {
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
            throw new IllegalStateException("Failed to load RSA public key", e);
        }
    }
}
