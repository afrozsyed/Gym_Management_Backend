package com.gym.member.dto;

import com.gym.common.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberRequest {
    @NotBlank(message = "fullName is required")
    @Size(min = 3, message = "fullName must be at least 3 characters")
    private String fullName;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "\\d{10}", message = "phone must be 10 digits")
    private String phone;

    @Email(message = "email must be valid")
    private String email;

    private Gender gender;
    private LocalDate dob;
    private String address;

    @NotNull(message = "joinedDate is required")
    private LocalDate joinedDate;
}
