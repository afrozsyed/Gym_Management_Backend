package com.gym.user.service;

import com.gym.common.exception.ResourceNotFoundException;
import com.gym.user.dto.UserResponse;
import com.gym.user.entity.User;
import com.gym.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public void enableUser(Long id) {
        User user = getUser(id);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(Long id) {
        User user = getUser(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(user.getRole())
            .enabled(user.isEnabled())
            .createdAt(user.getCreatedAt())
            .build();
    }
}
