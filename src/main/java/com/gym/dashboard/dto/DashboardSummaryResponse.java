package com.gym.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardSummaryResponse {
    private long totalMembers;
    private long activeMemberships;
    private long expiredMemberships;
    private long todayAttendance;
    private BigDecimal monthlyRevenue;
}
