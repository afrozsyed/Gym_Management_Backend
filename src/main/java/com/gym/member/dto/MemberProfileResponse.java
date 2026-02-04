package com.gym.member.dto;

import com.gym.common.model.MemberStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MemberProfileResponse {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private LocalDate joinedDate;
    private MemberStatus status;
    private int totalMemberships;
    private int totalAttendance;
    private BigDecimal totalPayments;
}
