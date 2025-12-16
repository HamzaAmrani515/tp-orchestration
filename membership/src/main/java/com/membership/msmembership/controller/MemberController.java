package com.membership.msmembership.controller;

import com.membership.msmembership.domain.Member;
import com.membership.msmembership.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public List<Member> getAll() {
        return memberService.getAll();
    }

    @PostMapping
    public Member create(@RequestBody @Valid Member member) {
        return memberService.create(member);
    }
}
