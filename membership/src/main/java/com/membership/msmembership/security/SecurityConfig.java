package com.membership.msmembership.security;

import com.membership.msmembership.security.crypto.PemKeyLoader;
import com.membership.msmembership.security.jwt.JwtAuthenticationFilter;
import com.membership.msmembership.security.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Duration;

@Configuration
public class SecurityConfig {

    // endpoints publics
    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/login",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/h2-console/**",
            "/actuator/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtService jwtService() {
        var privateKey = PemKeyLoader.loadPrivateKey(new ClassPathResource("keys/private_key.pem"));
        var publicKey = PemKeyLoader.loadPublicKey(new ClassPathResource("keys/public_key.pem"));
        return new JwtService(privateKey, publicKey, Duration.ofHours(1));
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
        // IMPORTANT: filtre bean (pas new ...) pour pouvoir l’exclure correctement
        return new JwtAuthenticationFilter(jwtService, PUBLIC_ENDPOINTS);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(h -> h.frameOptions(f -> f.disable())) // h2-console
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
