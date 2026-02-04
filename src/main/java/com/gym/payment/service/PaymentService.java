package com.gym.payment.service;

import com.gym.payment.dto.PaymentRequest;
import com.gym.payment.dto.PaymentResponse;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    PaymentResponse create(PaymentRequest request);
    List<PaymentResponse> list(Long memberId, LocalDate from, LocalDate to);
    PaymentResponse getById(Long id);
}
