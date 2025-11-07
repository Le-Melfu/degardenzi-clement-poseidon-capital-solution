package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "curvepoint")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "asOfDate")
    private LocalDateTime asOfDate;

    @NotNull(message = "Term is mandatory")
    @PositiveOrZero(message = "Term must be positive or zero")
    @Column(name = "term", nullable = false)
    private BigDecimal term;

    @NotNull(message = "Value is mandatory")
    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Convenience constructor used by tests: (term, value)
    public CurvePoint(BigDecimal term, BigDecimal value) {
        this.term = term;
        this.value = value;
    }
}