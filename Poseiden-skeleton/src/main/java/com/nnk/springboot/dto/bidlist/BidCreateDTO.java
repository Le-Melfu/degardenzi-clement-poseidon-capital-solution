package com.nnk.springboot.dto.bidlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidCreateDTO {
    @NotBlank(message = "Account is mandatory")
    private String account;

    private String type;

    @Positive(message = "Bid quantity must be positive")
    private BigDecimal bidQuantity;
}
