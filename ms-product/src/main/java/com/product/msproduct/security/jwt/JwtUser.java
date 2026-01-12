package com.product.msproduct.security.jwt;

import java.util.List;

public record JwtUser(Long userId, String email, List<String> roles) {}
