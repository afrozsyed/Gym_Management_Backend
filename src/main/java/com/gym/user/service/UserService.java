package com.gym.user.service;

import com.gym.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> listUsers();
    void enableUser(Long id);
    void disableUser(Long id);
}
