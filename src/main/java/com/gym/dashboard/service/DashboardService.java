package com.gym.dashboard.service;

import com.gym.dashboard.dto.DashboardSummaryResponse;
import com.gym.dashboard.dto.RevenueReportResponse;

public interface DashboardService {
    DashboardSummaryResponse summary();
    RevenueReportResponse revenue(int month, int year);
}
