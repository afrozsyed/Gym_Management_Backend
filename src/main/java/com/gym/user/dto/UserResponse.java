package com.gym.user.dto;

import com.gym.common.model.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private Role role;
    private boolean enabled;
    private LocalDateTime createdAt;
}
