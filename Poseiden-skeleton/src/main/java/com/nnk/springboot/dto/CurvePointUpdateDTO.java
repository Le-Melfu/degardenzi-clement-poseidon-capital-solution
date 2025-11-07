package com.nnk.springboot.dto;

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
public class CurvePointUpdateDTO {
    private LocalDateTime asOfDate;

    @PositiveOrZero(message = "Term must be positive or zero")
    private BigDecimal term;

    private BigDecimal value;
}

