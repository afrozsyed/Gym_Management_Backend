package com.gym.member.service;

import com.gym.common.model.MemberStatus;
import com.gym.member.dto.MemberProfileResponse;
import com.gym.member.dto.MemberRequest;
import com.gym.member.dto.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {
    MemberResponse create(MemberRequest request);
    MemberResponse update(Long id, MemberRequest request);
    Page<MemberResponse> list(String search, MemberStatus status, Pageable pageable);
    MemberProfileResponse getProfile(Long id);
    MemberResponse getById(Long id);
    void deactivate(Long id);
    void activate(Long id);
}
