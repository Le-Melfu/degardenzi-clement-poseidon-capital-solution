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
public class TradeResponseDTO {
    private Long id;
    private String account;
    private String type;
    private BigDecimal buyQuantity;
    private BigDecimal sellQuantity;
    private LocalDateTime tradeDate;
}

