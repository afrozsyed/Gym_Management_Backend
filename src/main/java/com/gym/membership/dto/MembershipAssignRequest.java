package com.gym.membership.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MembershipAssignRequest {
    @NotNull(message = "memberId is required")
    private Long memberId;

    @NotNull(message = "planId is required")
    private Long planId;

    @NotNull(message = "startDate is required")
    private LocalDate startDate;
}
