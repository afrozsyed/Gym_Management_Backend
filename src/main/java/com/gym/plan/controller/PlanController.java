package com.gym.plan.controller;

import com.gym.common.response.ApiResponse;
import com.gym.plan.dto.PlanRequest;
import com.gym.plan.dto.PlanResponse;
import com.gym.plan.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@PreAuthorize("hasRole('ADMIN')")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PlanResponse>> create(@Valid @RequestBody PlanRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Plan created", planService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlanResponse>>> list() {
        return ResponseEntity.ok(ApiResponse.success("Plans fetched", planService.listActive()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlanResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Plan fetched", planService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PlanResponse>> update(@PathVariable Long id,
                                                            @Valid @RequestBody PlanRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Plan updated", planService.update(id, request)));
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<Void>> disable(@PathVariable Long id) {
        planService.disable(id);
        return ResponseEntity.ok(ApiResponse.success("Plan disabled", null));
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<ApiResponse<Void>> enable(@PathVariable Long id) {
        planService.enable(id);
        return ResponseEntity.ok(ApiResponse.success("Plan enabled", null));
    }
}
