package com.nnk.springboot.dto.rating;

import com.nnk.springboot.domain.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponseDTO {
    private Long id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;

    /** Converts this DTO to a Rating entity for form binding. */
    public Rating toRating() {
        return Rating.builder()
                .id(id)
                .moodysRating(moodysRating)
                .sandPRating(sandPRating)
                .fitchRating(fitchRating)
                .orderNumber(orderNumber)
                .build();
    }
}
