package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurvePointCreateDTO {
    private Integer curveId;
    private LocalDateTime asOfDate;

    @NotNull(message = "Term is mandatory")
    @PositiveOrZero(message = "Term must be positive or zero")
    private BigDecimal term;

    @NotNull(message = "Value is mandatory")
    private BigDecimal value;
}

