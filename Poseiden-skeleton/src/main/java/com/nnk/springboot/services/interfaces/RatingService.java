package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingCreateDTO;
import com.nnk.springboot.dto.RatingResponseDTO;
import com.nnk.springboot.dto.RatingUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingService {
    RatingResponseDTO create(RatingCreateDTO dto);
    RatingResponseDTO findById(Long id);
    Page<RatingResponseDTO> findAll(Pageable pageable, String moodysRating);
    RatingResponseDTO update(Long id, RatingUpdateDTO dto);
    void delete(Long id);
    RatingResponseDTO toResponseDTO(Rating rating);
}

