package com.nnk.springboot.services.implementations;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointCreateDTO;
import com.nnk.springboot.dto.CurvePointResponseDTO;
import com.nnk.springboot.dto.CurvePointUpdateDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.interfaces.CurvePointService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public CurvePointResponseDTO create(CurvePointCreateDTO dto) {
        CurvePoint curvePoint = CurvePoint.builder()
                .asOfDate(dto.getAsOfDate())
                .term(dto.getTerm())
                .value(dto.getValue())
                .build();

        CurvePoint saved = curvePointRepository.save(curvePoint);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CurvePointResponseDTO findById(Long id) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found with id: " + id));
        return toResponseDTO(curvePoint);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CurvePointResponseDTO> findAll(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {
        Specification<CurvePoint> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (startDate != null && endDate != null) {
                predicates.add(cb.between(root.get("asOfDate"), startDate, endDate));
            } else if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("asOfDate"), startDate));
            } else if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("asOfDate"), endDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return curvePointRepository.findAll(spec, pageable)
                .map(this::toResponseDTO);
    }

    @Override
    public CurvePointResponseDTO update(Long id, CurvePointUpdateDTO dto) {
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found with id: " + id));

        if (dto.getAsOfDate() != null) {
            curvePoint.setAsOfDate(dto.getAsOfDate());
        }
        if (dto.getTerm() != null) {
            curvePoint.setTerm(dto.getTerm());
        }
        if (dto.getValue() != null) {
            curvePoint.setValue(dto.getValue());
        }

        CurvePoint updated = curvePointRepository.save(curvePoint);
        return toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!curvePointRepository.existsById(id)) {
            throw new IllegalArgumentException("CurvePoint not found with id: " + id);
        }
        curvePointRepository.deleteById(id);
    }

    @Override
    public CurvePointResponseDTO toResponseDTO(CurvePoint curvePoint) {
        return CurvePointResponseDTO.builder()
                .id(curvePoint.getId())
                .asOfDate(curvePoint.getAsOfDate())
                .term(curvePoint.getTerm())
                .value(curvePoint.getValue())
                .createdAt(curvePoint.getCreatedAt())
                .updatedAt(curvePoint.getUpdatedAt())
                .build();
    }
}

