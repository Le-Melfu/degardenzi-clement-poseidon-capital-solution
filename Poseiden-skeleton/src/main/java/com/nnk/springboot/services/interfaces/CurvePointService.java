package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointCreateDTO;
import com.nnk.springboot.dto.CurvePointResponseDTO;
import com.nnk.springboot.dto.CurvePointUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CurvePointService {
    CurvePointResponseDTO create(CurvePointCreateDTO dto);
    CurvePointResponseDTO findById(Long id);
    Page<CurvePointResponseDTO> findAll(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
    CurvePointResponseDTO update(Long id, CurvePointUpdateDTO dto);
    void delete(Long id);
    CurvePointResponseDTO toResponseDTO(CurvePoint curvePoint);
}

