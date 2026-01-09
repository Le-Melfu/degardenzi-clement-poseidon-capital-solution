package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;

@Entity
@Table(name = "bidlist")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Long id;

    @NotBlank(message = "Account is mandatory")
    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "type")
    private String type;

    @Positive(message = "Bid quantity must be positive")
    @Column(name = "bidQuantity")
    private BigDecimal bidQuantity;

    // Convenience constructor used by tests: (account, type, bidQuantity)
    public BidList(String account, String type, BigDecimal bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}