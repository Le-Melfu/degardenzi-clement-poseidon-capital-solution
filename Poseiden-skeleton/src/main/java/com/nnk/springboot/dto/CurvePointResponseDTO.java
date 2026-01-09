package com.nnk.springboot.dto;

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
public class CurvePointResponseDTO {
    private Long id;
    private Integer curveId;
    private LocalDateTime asOfDate;
    private BigDecimal term;
    private BigDecimal value;
}

