package com.gym.attendance.service;

import com.gym.attendance.dto.AttendanceRequest;
import com.gym.attendance.dto.AttendanceResponse;
import com.gym.attendance.entity.Attendance;
import com.gym.attendance.repository.AttendanceRepository;
import com.gym.common.exception.BadRequestException;
import com.gym.common.exception.ResourceNotFoundException;
import com.gym.member.entity.Member;
import com.gym.member.repository.MemberRepository;
import com.gym.user.entity.User;
import com.gym.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                 MemberRepository memberRepository,
                                 UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AttendanceResponse checkIn(AttendanceRequest request) {
        Member member = getMember(request.getMemberId());
        LocalDate today = LocalDate.now();
        attendanceRepository.findByMemberIdAndDate(member.getId(), today)
            .ifPresent(existing -> {
                throw new BadRequestException("Member already checked in today");
            });
        Attendance attendance = Attendance.builder()
            .member(member)
            .date(today)
            .checkInTime(LocalTime.now())
            .markedByUser(getCurrentUser())
            .createdAt(LocalDateTime.now())
            .build();
        return mapToResponse(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceResponse checkOut(AttendanceRequest request) {
        Member member = getMember(request.getMemberId());
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByMemberIdAndDate(member.getId(), today)
            .orElseThrow(() -> new BadRequestException("Member has not checked in today"));
        if (attendance.getCheckOutTime() != null) {
            throw new BadRequestException("Member already checked out today");
        }
        attendance.setCheckOutTime(LocalTime.now());
        return mapToResponse(attendanceRepository.save(attendance));
    }

    @Override
    public List<AttendanceResponse> today() {
        return attendanceRepository.findByDate(LocalDate.now()).stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public List<AttendanceResponse> report(LocalDate from, LocalDate to) {
        return attendanceRepository.findByDateBetween(from, to).stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public List<AttendanceResponse> memberHistory(Long memberId) {
        return attendanceRepository.findByMemberIdOrderByDateDesc(memberId).stream()
            .map(this::mapToResponse)
            .toList();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return userRepository.findByUsername(authentication.getName())
            .orElse(null);
    }

    private AttendanceResponse mapToResponse(Attendance attendance) {
        return AttendanceResponse.builder()
            .id(attendance.getId())
            .memberId(attendance.getMember().getId())
            .memberName(attendance.getMember().getFullName())
            .date(attendance.getDate())
            .checkInTime(attendance.getCheckInTime())
            .checkOutTime(attendance.getCheckOutTime())
            .build();
    }
}
