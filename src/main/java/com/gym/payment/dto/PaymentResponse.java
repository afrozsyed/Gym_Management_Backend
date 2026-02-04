package com.gym.payment.dto;

import com.gym.common.model.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Long memberId;
    private String memberName;
    private Long membershipId;
    private BigDecimal amountPaid;
    private LocalDate paymentDate;
    private PaymentMethod method;
    private String remarks;
    private LocalDateTime createdAt;
}
