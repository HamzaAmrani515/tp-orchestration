package com.membership.msmembership.service;

import com.membership.msmembership.domain.Member;
import com.membership.msmembership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    public List<Member> getAll() {
        return repository.findAll();
    }

    public Member getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public Member create(Member member) {
        member.setId(null);
        return repository.save(member);
    }
}
