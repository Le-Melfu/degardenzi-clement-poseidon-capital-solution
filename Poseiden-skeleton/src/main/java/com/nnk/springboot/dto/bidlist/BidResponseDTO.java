package com.nnk.springboot.dto.bidlist;

import com.nnk.springboot.domain.BidList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidResponseDTO {
    private Long id;
    private String account;
    private String type;
    private BigDecimal bidQuantity;

    /** Converts this DTO to a BidList entity for form binding. */
    public BidList toBidList() {
        return BidList.builder()
                .id(id)
                .account(account)
                .type(type)
                .bidQuantity(bidQuantity)
                .build();
    }
}
