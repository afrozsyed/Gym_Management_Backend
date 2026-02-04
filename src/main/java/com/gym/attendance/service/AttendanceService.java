package com.gym.attendance.service;

import com.gym.attendance.dto.AttendanceRequest;
import com.gym.attendance.dto.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    AttendanceResponse checkIn(AttendanceRequest request);
    AttendanceResponse checkOut(AttendanceRequest request);
    List<AttendanceResponse> today();
    List<AttendanceResponse> report(LocalDate from, LocalDate to);
    List<AttendanceResponse> memberHistory(Long memberId);
}
