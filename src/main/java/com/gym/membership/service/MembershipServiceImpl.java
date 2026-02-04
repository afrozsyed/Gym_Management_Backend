package com.gym.membership.service;

import com.gym.common.exception.BadRequestException;
import com.gym.common.exception.ResourceNotFoundException;
import com.gym.common.model.MembershipStatus;
import com.gym.member.entity.Member;
import com.gym.member.repository.MemberRepository;
import com.gym.membership.dto.MembershipAssignRequest;
import com.gym.membership.dto.MembershipRenewRequest;
import com.gym.membership.dto.MembershipResponse;
import com.gym.membership.entity.Membership;
import com.gym.membership.repository.MembershipRepository;
import com.gym.plan.entity.MembershipPlan;
import com.gym.plan.repository.MembershipPlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {
    private final MembershipRepository membershipRepository;
    private final MemberRepository memberRepository;
    private final MembershipPlanRepository planRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository,
                                 MemberRepository memberRepository,
                                 MembershipPlanRepository planRepository) {
        this.membershipRepository = membershipRepository;
        this.memberRepository = memberRepository;
        this.planRepository = planRepository;
    }

    @Override
    public MembershipResponse assign(MembershipAssignRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        MembershipPlan plan = planRepository.findById(request.getPlanId())
            .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
        membershipRepository.findFirstByMemberAndStatus(member, MembershipStatus.ACTIVE)
            .ifPresent(existing -> {
                throw new BadRequestException("Member already has an active membership");
            });
        Membership membership = buildMembership(member, plan, request.getStartDate());
        return mapToResponse(membershipRepository.save(membership));
    }

    @Override
    public MembershipResponse renew(Long id, MembershipRenewRequest request) {
        Membership existing = membershipRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Membership not found"));
        MembershipPlan plan = planRepository.findById(request.getPlanId())
            .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
        existing.setPlan(plan);
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getStartDate().plusDays(plan.getDurationDays()));
        existing.setStatus(resolveStatus(existing.getEndDate()));
        return mapToResponse(membershipRepository.save(existing));
    }

    @Override
    public List<MembershipResponse> historyByMember(Long memberId) {
        refreshExpired();
        return membershipRepository.findByMemberIdOrderByStartDateDesc(memberId).stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public List<MembershipResponse> listActive() {
        refreshExpired();
        return membershipRepository.findByStatus(MembershipStatus.ACTIVE).stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public List<MembershipResponse> listExpired() {
        refreshExpired();
        return membershipRepository.findByStatus(MembershipStatus.EXPIRED).stream()
            .map(this::mapToResponse)
            .toList();
    }

    private void refreshExpired() {
        LocalDate today = LocalDate.now();
        membershipRepository.findByEndDateBefore(today).forEach(membership -> {
            if (membership.getStatus() != MembershipStatus.EXPIRED) {
                membership.setStatus(MembershipStatus.EXPIRED);
                membershipRepository.save(membership);
            }
        });
    }

    private Membership buildMembership(Member member, MembershipPlan plan, LocalDate startDate) {
        LocalDate endDate = startDate.plusDays(plan.getDurationDays());
        MembershipStatus status = resolveStatus(endDate);
        return Membership.builder()
            .member(member)
            .plan(plan)
            .startDate(startDate)
            .endDate(endDate)
            .status(status)
            .createdAt(LocalDateTime.now())
            .build();
    }

    private MembershipStatus resolveStatus(LocalDate endDate) {
        return endDate.isBefore(LocalDate.now()) ? MembershipStatus.EXPIRED : MembershipStatus.ACTIVE;
    }

    private MembershipResponse mapToResponse(Membership membership) {
        return MembershipResponse.builder()
            .id(membership.getId())
            .memberId(membership.getMember().getId())
            .memberName(membership.getMember().getFullName())
            .planId(membership.getPlan().getId())
            .planName(membership.getPlan().getName())
            .startDate(membership.getStartDate())
            .endDate(membership.getEndDate())
            .status(membership.getStatus())
            .build();
    }
}
