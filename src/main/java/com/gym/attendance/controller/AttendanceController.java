package com.gym.attendance.controller;

import com.gym.attendance.dto.AttendanceRequest;
import com.gym.attendance.dto.AttendanceResponse;
import com.gym.attendance.service.AttendanceService;
import com.gym.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/checkin")
    public ResponseEntity<ApiResponse<AttendanceResponse>> checkIn(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Checked in", attendanceService.checkIn(request)));
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<AttendanceResponse>> checkOut(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Checked out", attendanceService.checkOut(request)));
    }

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> today() {
        return ResponseEntity.ok(ApiResponse.success("Today attendance", attendanceService.today()));
    }

    @GetMapping("/report")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> report(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(ApiResponse.success("Attendance report", attendanceService.report(from, to)));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> memberHistory(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.success("Attendance history", attendanceService.memberHistory(memberId)));
    }
}
