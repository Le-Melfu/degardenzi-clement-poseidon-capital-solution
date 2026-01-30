package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvepoint.CurvePointCreateDTO;
import com.nnk.springboot.dto.curvepoint.CurvePointResponseDTO;
import com.nnk.springboot.dto.curvepoint.CurvePointUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/** Service interface for CurvePoint CRUD operations. */
public interface CurvePointService {
    CurvePointResponseDTO create(CurvePointCreateDTO dto);
    CurvePointResponseDTO findById(Long id);
    /** Returns a CurvePoint ready for the update form (converted from DTO). */
    CurvePoint getForUpdateForm(Long id);
    Page<CurvePointResponseDTO> findAll(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
    CurvePointResponseDTO update(Long id, CurvePointUpdateDTO dto);
    void delete(Long id);
    CurvePointResponseDTO toResponseDTO(CurvePoint curvePoint);
}

