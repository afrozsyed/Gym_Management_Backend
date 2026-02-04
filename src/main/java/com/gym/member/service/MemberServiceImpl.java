package com.gym.member.service;

import com.gym.common.exception.ResourceNotFoundException;
import com.gym.common.model.MemberStatus;
import com.gym.member.dto.MemberProfileResponse;
import com.gym.member.dto.MemberRequest;
import com.gym.member.dto.MemberResponse;
import com.gym.member.entity.Member;
import com.gym.member.repository.MemberRepository;
import com.gym.membership.repository.MembershipRepository;
import com.gym.payment.repository.PaymentRepository;
import com.gym.attendance.repository.AttendanceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentRepository paymentRepository;
    private final AttendanceRepository attendanceRepository;

    public MemberServiceImpl(MemberRepository memberRepository,
                             MembershipRepository membershipRepository,
                             PaymentRepository paymentRepository,
                             AttendanceRepository attendanceRepository) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.paymentRepository = paymentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public MemberResponse create(MemberRequest request) {
        Member member = mapToEntity(request);
        member.setStatus(MemberStatus.ACTIVE);
        return mapToResponse(memberRepository.save(member));
    }

    @Override
    public MemberResponse update(Long id, MemberRequest request) {
        Member member = getMember(id);
        member.setFullName(request.getFullName());
        member.setPhone(request.getPhone());
        member.setEmail(request.getEmail());
        member.setGender(request.getGender());
        member.setDob(request.getDob());
        member.setAddress(request.getAddress());
        member.setJoinedDate(request.getJoinedDate());
        return mapToResponse(memberRepository.save(member));
    }

    @Override
    public Page<MemberResponse> list(String search, MemberStatus status, Pageable pageable) {
        return memberRepository.searchMembers(search, status, pageable)
            .map(this::mapToResponse);
    }

    @Override
    public MemberProfileResponse getProfile(Long id) {
        Member member = getMember(id);
        int memberships = membershipRepository.findByMemberIdOrderByStartDateDesc(id).size();
        int attendanceCount = attendanceRepository.findByMemberIdOrderByDateDesc(id).size();
        BigDecimal totalPayments = paymentRepository.findByFilters(id, null, null).stream()
            .map(payment -> payment.getAmountPaid())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return MemberProfileResponse.builder()
            .id(member.getId())
            .fullName(member.getFullName())
            .phone(member.getPhone())
            .email(member.getEmail())
            .joinedDate(member.getJoinedDate())
            .status(member.getStatus())
            .totalMemberships(memberships)
            .totalAttendance(attendanceCount)
            .totalPayments(totalPayments)
            .build();
    }

    @Override
    public MemberResponse getById(Long id) {
        return mapToResponse(getMember(id));
    }

    @Override
    public void deactivate(Long id) {
        Member member = getMember(id);
        member.setStatus(MemberStatus.INACTIVE);
        memberRepository.save(member);
    }

    @Override
    public void activate(Long id) {
        Member member = getMember(id);
        member.setStatus(MemberStatus.ACTIVE);
        memberRepository.save(member);
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
    }

    private Member mapToEntity(MemberRequest request) {
        return Member.builder()
            .fullName(request.getFullName())
            .phone(request.getPhone())
            .email(request.getEmail())
            .gender(request.getGender())
            .dob(request.getDob())
            .address(request.getAddress())
            .joinedDate(request.getJoinedDate())
            .build();
    }

    private MemberResponse mapToResponse(Member member) {
        return MemberResponse.builder()
            .id(member.getId())
            .fullName(member.getFullName())
            .phone(member.getPhone())
            .email(member.getEmail())
            .gender(member.getGender())
            .dob(member.getDob())
            .address(member.getAddress())
            .joinedDate(member.getJoinedDate())
            .status(member.getStatus())
            .build();
    }
}
