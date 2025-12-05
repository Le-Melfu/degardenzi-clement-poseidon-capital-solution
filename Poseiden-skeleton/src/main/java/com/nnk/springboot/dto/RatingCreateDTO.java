package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingCreateDTO {
    private String moodysRating;

    private String sandPRating;

    private String fitchRating;

    @NotNull(message = "Order number is mandatory")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;
}

