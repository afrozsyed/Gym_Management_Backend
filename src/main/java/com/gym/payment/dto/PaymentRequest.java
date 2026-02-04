package com.gym.payment.dto;

import com.gym.common.model.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRequest {
    @NotNull(message = "memberId is required")
    private Long memberId;

    @NotNull(message = "membershipId is required")
    private Long membershipId;

    @NotNull(message = "amountPaid is required")
    @DecimalMin(value = "1.0", message = "amountPaid must be at least 1")
    private BigDecimal amountPaid;

    @NotNull(message = "paymentDate is required")
    private LocalDate paymentDate;

    @NotNull(message = "method is required")
    private PaymentMethod method;

    private String remarks;
}
