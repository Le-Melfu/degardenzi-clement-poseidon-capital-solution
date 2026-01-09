package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Long id;

    @NotBlank(message = "Account is mandatory")
    @Column(name = "account")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Column(name = "type")
    private String type;

    @PositiveOrZero(message = "Buy quantity must be positive or zero")
    @Column(name = "buyQuantity")
    private BigDecimal buyQuantity;

    @PositiveOrZero(message = "Sell quantity must be positive or zero")
    @Column(name = "sellQuantity")
    private BigDecimal sellQuantity;

    @Column(name = "tradeDate")
    private LocalDateTime tradeDate;

    // Convenience constructor used by tests: (account, type)
    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }
}