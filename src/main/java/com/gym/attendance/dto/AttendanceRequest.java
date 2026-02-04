package com.gym.attendance.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceRequest {
    @NotNull(message = "memberId is required")
    private Long memberId;
}
