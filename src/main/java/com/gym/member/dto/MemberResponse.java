package com.gym.member.dto;

import com.gym.common.model.Gender;
import com.gym.common.model.MemberStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MemberResponse {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private Gender gender;
    private LocalDate dob;
    private String address;
    private LocalDate joinedDate;
    private MemberStatus status;
}
