package com.nnk.springboot.dto.trade;

import jakarta.validation.constraints.NotBlank;
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
public class TradeCreateDTO {
    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @PositiveOrZero(message = "Buy quantity must be positive or zero")
    private BigDecimal buyQuantity;

    @PositiveOrZero(message = "Sell quantity must be positive or zero")
    private BigDecimal sellQuantity;

    private LocalDateTime tradeDate;
}
