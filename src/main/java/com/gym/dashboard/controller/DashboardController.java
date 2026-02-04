package com.gym.dashboard.controller;

import com.gym.common.response.ApiResponse;
import com.gym.dashboard.dto.DashboardSummaryResponse;
import com.gym.dashboard.dto.RevenueReportResponse;
import com.gym.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> summary() {
        return ResponseEntity.ok(ApiResponse.success("Dashboard summary", dashboardService.summary()));
    }

    @GetMapping("/revenue")
    public ResponseEntity<ApiResponse<RevenueReportResponse>> revenue(@RequestParam int month,
                                                                      @RequestParam int year) {
        return ResponseEntity.ok(ApiResponse.success("Revenue report", dashboardService.revenue(month, year)));
    }
}
