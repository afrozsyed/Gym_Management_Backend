package com.gym.plan.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "durationDays is required")
    @Min(value = 1, message = "durationDays must be at least 1")
    private Integer durationDays;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "price must be at least 0")
    private BigDecimal price;

    private String description;
}
