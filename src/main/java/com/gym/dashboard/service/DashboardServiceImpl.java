package com.gym.dashboard.service;

import com.gym.attendance.repository.AttendanceRepository;
import com.gym.common.model.MembershipStatus;
import com.gym.dashboard.dto.DashboardSummaryResponse;
import com.gym.dashboard.dto.RevenueReportResponse;
import com.gym.member.repository.MemberRepository;
import com.gym.membership.repository.MembershipRepository;
import com.gym.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final AttendanceRepository attendanceRepository;
    private final PaymentRepository paymentRepository;

    public DashboardServiceImpl(MemberRepository memberRepository,
                                MembershipRepository membershipRepository,
                                AttendanceRepository attendanceRepository,
                                PaymentRepository paymentRepository) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.attendanceRepository = attendanceRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public DashboardSummaryResponse summary() {
        long totalMembers = memberRepository.count();
        long activeMemberships = membershipRepository.findByStatus(MembershipStatus.ACTIVE).size();
        long expiredMemberships = membershipRepository.findByStatus(MembershipStatus.EXPIRED).size();
        long todayAttendance = attendanceRepository.findByDate(LocalDate.now()).size();
        LocalDate now = LocalDate.now();
        BigDecimal monthlyRevenue = revenue(now.getMonthValue(), now.getYear()).getRevenue();
        return DashboardSummaryResponse.builder()
            .totalMembers(totalMembers)
            .activeMemberships(activeMemberships)
            .expiredMemberships(expiredMemberships)
            .todayAttendance(todayAttendance)
            .monthlyRevenue(monthlyRevenue)
            .build();
    }

    @Override
    public RevenueReportResponse revenue(int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        BigDecimal revenue = paymentRepository.findByFilters(null, start, end).stream()
            .map(payment -> payment.getAmountPaid())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return RevenueReportResponse.builder()
            .month(month)
            .year(year)
            .revenue(revenue)
            .build();
    }
}
