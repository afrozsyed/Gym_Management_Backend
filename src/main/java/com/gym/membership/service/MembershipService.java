package com.gym.membership.service;

import com.gym.membership.dto.MembershipAssignRequest;
import com.gym.membership.dto.MembershipRenewRequest;
import com.gym.membership.dto.MembershipResponse;

import java.util.List;

public interface MembershipService {
    MembershipResponse assign(MembershipAssignRequest request);
    MembershipResponse renew(Long id, MembershipRenewRequest request);
    List<MembershipResponse> historyByMember(Long memberId);
    List<MembershipResponse> listActive();
    List<MembershipResponse> listExpired();
}
