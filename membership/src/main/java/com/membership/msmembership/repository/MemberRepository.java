package com.membership.msmembership.repository;

import com.membership.msmembership.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
