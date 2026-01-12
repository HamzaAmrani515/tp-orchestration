package com.membership.msmembership.web;

import com.membership.msmembership.domain.Member;
import com.membership.msmembership.repository.MemberRepository;
import com.membership.msmembership.security.jwt.JwtService;
import com.membership.msmembership.web.dto.AuthLoginRequest;
import com.membership.msmembership.web.dto.AuthLoginResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(MemberRepository memberRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthLoginResponse login(@Valid @RequestBody AuthLoginRequest request) {
        String email = request.email().trim().toLowerCase();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials (email not found)"));

        if (!passwordEncoder.matches(request.password(), member.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials (wrong password)");
        }

        String token = jwtService.generateToken(member.getId(), member.getEmail(), member.getRoles());
        return new AuthLoginResponse(token, jwtService.expiresInSeconds());
    }
}
