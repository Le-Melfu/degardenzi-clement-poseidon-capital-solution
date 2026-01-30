package com.nnk.springboot.dto.curvepoint;

import com.nnk.springboot.domain.CurvePoint;
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

    /** Converts this DTO to a CurvePoint entity for form binding. */
    public CurvePoint toCurvePoint() {
        return CurvePoint.builder()
                .id(id)
                .curveId(curveId)
                .asOfDate(asOfDate)
                .term(term)
                .value(value)
                .build();
    }
}
