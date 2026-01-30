package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingCreateDTO;
import com.nnk.springboot.dto.rating.RatingResponseDTO;
import com.nnk.springboot.dto.rating.RatingUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Service interface for Rating CRUD operations. */
public interface RatingService {
    RatingResponseDTO create(RatingCreateDTO dto);
    RatingResponseDTO findById(Long id);
    /** Returns a Rating ready for the update form (converted from DTO). */
    Rating getForUpdateForm(Long id);
    Page<RatingResponseDTO> findAll(Pageable pageable, String moodysRating);
    RatingResponseDTO update(Long id, RatingUpdateDTO dto);
    void delete(Long id);
    RatingResponseDTO toResponseDTO(Rating rating);
}

