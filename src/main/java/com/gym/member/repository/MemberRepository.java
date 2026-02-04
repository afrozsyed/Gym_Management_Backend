package com.gym.member.repository;

import com.gym.common.model.MemberStatus;
import com.gym.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where (:status is null or m.status = :status) and (" +
        ":search is null or lower(m.fullName) like lower(concat('%', :search, '%')) or m.phone like concat('%', :search, '%'))")
    Page<Member> searchMembers(@Param("search") String search, @Param("status") MemberStatus status, Pageable pageable);
}
