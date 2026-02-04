package com.gym.attendance.repository;

import com.gym.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByMemberIdAndDate(Long memberId, LocalDate date);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByDateBetween(LocalDate from, LocalDate to);
    List<Attendance> findByMemberIdOrderByDateDesc(Long memberId);
}
