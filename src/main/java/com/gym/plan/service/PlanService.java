package com.gym.plan.service;

import com.gym.plan.dto.PlanRequest;
import com.gym.plan.dto.PlanResponse;

import java.util.List;

public interface PlanService {
    PlanResponse create(PlanRequest request);
    PlanResponse update(Long id, PlanRequest request);
    PlanResponse getById(Long id);
    List<PlanResponse> listActive();
    void enable(Long id);
    void disable(Long id);
}
