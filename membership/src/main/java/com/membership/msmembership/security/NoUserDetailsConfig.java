package com.membership.msmembership.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class NoUserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UnsupportedOperationException("UserDetailsService is disabled. Use /api/v1/auth/login with JWT.");
        };
    }
}
