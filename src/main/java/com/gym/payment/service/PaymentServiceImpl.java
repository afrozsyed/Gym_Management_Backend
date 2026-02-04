package com.gym.payment.service;

import com.gym.common.exception.BadRequestException;
import com.gym.common.exception.ResourceNotFoundException;
import com.gym.member.entity.Member;
import com.gym.member.repository.MemberRepository;
import com.gym.membership.entity.Membership;
import com.gym.membership.repository.MembershipRepository;
import com.gym.payment.dto.PaymentRequest;
import com.gym.payment.dto.PaymentResponse;
import com.gym.payment.entity.Payment;
import com.gym.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              MemberRepository memberRepository,
                              MembershipRepository membershipRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public PaymentResponse create(PaymentRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Membership membership = membershipRepository.findById(request.getMembershipId())
            .orElseThrow(() -> new ResourceNotFoundException("Membership not found"));
        if (!membership.getMember().getId().equals(member.getId())) {
            throw new BadRequestException("Membership does not belong to member");
        }
        Payment payment = Payment.builder()
            .member(member)
            .membership(membership)
            .amountPaid(request.getAmountPaid())
            .paymentDate(request.getPaymentDate())
            .method(request.getMethod())
            .remarks(request.getRemarks())
            .createdAt(LocalDateTime.now())
            .build();
        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentResponse> list(Long memberId, LocalDate from, LocalDate to) {
        return paymentRepository.findByFilters(memberId, from, to).stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public PaymentResponse getById(Long id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
            .id(payment.getId())
            .memberId(payment.getMember().getId())
            .memberName(payment.getMember().getFullName())
            .membershipId(payment.getMembership().getId())
            .amountPaid(payment.getAmountPaid())
            .paymentDate(payment.getPaymentDate())
            .method(payment.getMethod())
            .remarks(payment.getRemarks())
            .createdAt(payment.getCreatedAt())
            .build();
    }
}
