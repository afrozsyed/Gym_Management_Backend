package com.gym.plan.service;

import com.gym.common.exception.ResourceNotFoundException;
import com.gym.plan.dto.PlanRequest;
import com.gym.plan.dto.PlanResponse;
import com.gym.plan.entity.MembershipPlan;
import com.gym.plan.repository.MembershipPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    private final MembershipPlanRepository planRepository;

    public PlanServiceImpl(MembershipPlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public PlanResponse create(PlanRequest request) {
        MembershipPlan plan = MembershipPlan.builder()
            .name(request.getName())
            .durationDays(request.getDurationDays())
            .price(request.getPrice())
            .description(request.getDescription())
            .isActive(true)
            .build();
        return mapToResponse(planRepository.save(plan));
    }

    @Override
    public PlanResponse update(Long id, PlanRequest request) {
        MembershipPlan plan = getPlan(id);
        plan.setName(request.getName());
        plan.setDurationDays(request.getDurationDays());
        plan.setPrice(request.getPrice());
        plan.setDescription(request.getDescription());
        return mapToResponse(planRepository.save(plan));
    }

    @Override
    public PlanResponse getById(Long id) {
        return mapToResponse(getPlan(id));
    }

    @Override
    public List<PlanResponse> listActive() {
        return planRepository.findByIsActiveTrue().stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    public void enable(Long id) {
        MembershipPlan plan = getPlan(id);
        plan.setActive(true);
        planRepository.save(plan);
    }

    @Override
    public void disable(Long id) {
        MembershipPlan plan = getPlan(id);
        plan.setActive(false);
        planRepository.save(plan);
    }

    private MembershipPlan getPlan(Long id) {
        return planRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
    }

    private PlanResponse mapToResponse(MembershipPlan plan) {
        return PlanResponse.builder()
            .id(plan.getId())
            .name(plan.getName())
            .durationDays(plan.getDurationDays())
            .price(plan.getPrice())
            .description(plan.getDescription())
            .isActive(plan.isActive())
            .build();
    }
}
