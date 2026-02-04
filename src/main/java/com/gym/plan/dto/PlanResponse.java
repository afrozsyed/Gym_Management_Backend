package com.gym.plan.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PlanResponse {
    private Long id;
    private String name;
    private Integer durationDays;
    private BigDecimal price;
    private String description;
    private boolean isActive;
}
