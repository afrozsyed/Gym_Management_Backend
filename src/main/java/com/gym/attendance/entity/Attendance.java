package com.gym.attendance.entity;

import com.gym.member.entity.Member;
import com.gym.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private LocalDate date;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    @ManyToOne
    @JoinColumn(name = "marked_by_user_id")
    private User markedByUser;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
