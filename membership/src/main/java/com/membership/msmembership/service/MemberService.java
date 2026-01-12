package com.membership.msmembership.service;

import com.membership.msmembership.domain.Member;
import com.membership.msmembership.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public List<Member> getAll() {
        return repository.findAll();
    }

    public Member getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public Member create(Member member) {
        Member toSave = new Member();
        toSave.setFirstName(member.getFirstName());
        toSave.setLastName(member.getLastName());
        toSave.setEmail(member.getEmail());
        toSave.setPasswordHash(member.getPasswordHash());
        toSave.setRoles(member.getRoles());
        toSave.setActive(member.isActive());

        return repository.save(toSave);
    }
}
