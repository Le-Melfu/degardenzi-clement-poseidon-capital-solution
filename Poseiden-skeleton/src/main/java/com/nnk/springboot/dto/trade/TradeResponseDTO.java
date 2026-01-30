package com.nnk.springboot.dto.trade;

import com.nnk.springboot.domain.Trade;
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

    /** Converts this DTO to a Trade entity for form binding. */
    public Trade toTrade() {
        return Trade.builder()
                .id(id)
                .account(account)
                .type(type)
                .buyQuantity(buyQuantity)
                .sellQuantity(sellQuantity)
                .tradeDate(tradeDate)
                .build();
    }
}
