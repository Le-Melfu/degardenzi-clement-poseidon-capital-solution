package com.nnk.springboot.dto.trade;

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
public class TradeUpdateDTO {
    private String account;

    private String type;

    @PositiveOrZero(message = "Buy quantity must be positive or zero")
    private BigDecimal buyQuantity;

    @PositiveOrZero(message = "Sell quantity must be positive or zero")
    private BigDecimal sellQuantity;

    private LocalDateTime tradeDate;
}
