package com.membership.msmembership.controller;

import com.membership.msmembership.domain.Member;
import com.membership.msmembership.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAll() {
        return memberService.getAll();
    }

    @GetMapping("/{id}")
    public Member getById(@PathVariable Long id) {
        return memberService.getById(id);
    }

    @PostMapping
    public Member create(@RequestBody @Valid Member member) {
        return memberService.create(member);
    }
}
