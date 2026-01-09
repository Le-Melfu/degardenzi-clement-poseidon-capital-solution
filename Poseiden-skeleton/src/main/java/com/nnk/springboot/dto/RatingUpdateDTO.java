package com.nnk.springboot.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingUpdateDTO {
    private String moodysRating;

    private String sandPRating;

    private String fitchRating;

    @Positive(message = "Order number must be positive")
    @Max(value = 127, message = "Order number must be between 1 and 127")
    private Integer orderNumber;
}

