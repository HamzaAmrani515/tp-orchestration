package com.membership.msmembership;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTool {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin123"));
    }
}
