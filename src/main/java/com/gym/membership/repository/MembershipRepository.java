package com.gym.membership.repository;

import com.gym.common.model.MembershipStatus;
import com.gym.member.entity.Member;
import com.gym.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByMemberIdOrderByStartDateDesc(Long memberId);
    List<Membership> findByStatus(MembershipStatus status);
    Optional<Membership> findFirstByMemberAndStatus(Member member, MembershipStatus status);
    List<Membership> findByEndDateBefore(LocalDate date);
}
