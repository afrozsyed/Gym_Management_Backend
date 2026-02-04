package com.gym.dashboard.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RevenueReportResponse {
    private int month;
    private int year;
    private BigDecimal revenue;
}
