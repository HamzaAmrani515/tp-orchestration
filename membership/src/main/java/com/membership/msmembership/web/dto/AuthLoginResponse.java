package com.membership.msmembership.web.dto;

public record AuthLoginResponse(String token, long expiresIn) {}
