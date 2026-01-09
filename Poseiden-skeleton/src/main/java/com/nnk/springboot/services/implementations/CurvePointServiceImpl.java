package com.nnk.springboot.services.implementations;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointCreateDTO;
import com.nnk.springboot.dto.CurvePointResponseDTO;
import com.nnk.springboot.dto.CurvePointUpdateDTO;
import com.nnk.springboot.exception.EntityNotFoundException;
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
import java.util.Objects;

/**
 * Service implementation for managing CurvePoint entities.
 * Provides CRUD operations and business logic for curve point management.
 */
@Service
@Transactional
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    /**
     * Constructs a new CurvePointServiceImpl with the given repository.
     *
     * @param curvePointRepository the repository for CurvePoint entities
     */
    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Creates a new curve point from the provided DTO.
     *
     * @param dto the DTO containing curve point creation data
     * @return the created curve point as a response DTO
     */
    @Override
    @SuppressWarnings("null")
    public CurvePointResponseDTO create(CurvePointCreateDTO dto) {
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(dto.getCurveId())
                .asOfDate(dto.getAsOfDate())
                .term(dto.getTerm())
                .value(dto.getValue())
                .build();

        CurvePoint saved = Objects.requireNonNull(curvePointRepository.save(curvePoint));
        return toResponseDTO(saved);
    }

    /**
     * Retrieves a curve point by its ID.
     *
     * @param id the ID of the curve point to retrieve
     * @return the curve point as a response DTO
     * @throws EntityNotFoundException if the curve point is not found
     */
    @Override
    @Transactional(readOnly = true)
    public CurvePointResponseDTO findById(Long id) {
        CurvePoint curvePoint = Objects.requireNonNull(curvePointRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("CurvePoint not found with id: " + id)));
        return toResponseDTO(curvePoint);
    }

    /**
     * Retrieves all curve points with optional filtering by date range.
     *
     * @param pageable pagination information
     * @param startDate optional start date for filtering (can be null)
     * @param endDate optional end date for filtering (can be null)
     * @return a page of curve point response DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CurvePointResponseDTO> findAll(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {
        Objects.requireNonNull(pageable);
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

    /**
     * Updates an existing curve point with the provided data.
     *
     * @param id the ID of the curve point to update
     * @param dto the DTO containing update data
     * @return the updated curve point as a response DTO
     * @throws EntityNotFoundException if the curve point is not found
     */
    @Override
    public CurvePointResponseDTO update(Long id, CurvePointUpdateDTO dto) {
        CurvePoint curvePoint = Objects.requireNonNull(curvePointRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("CurvePoint not found with id: " + id)));

        if (dto.getCurveId() != null) {
            curvePoint.setCurveId(dto.getCurveId());
        }
        if (dto.getAsOfDate() != null) {
            curvePoint.setAsOfDate(dto.getAsOfDate());
        }
        if (dto.getTerm() != null) {
            curvePoint.setTerm(dto.getTerm());
        }
        if (dto.getValue() != null) {
            curvePoint.setValue(dto.getValue());
        }

        CurvePoint updated = Objects.requireNonNull(curvePointRepository.save(curvePoint));
        return toResponseDTO(updated);
    }

    /**
     * Deletes a curve point by its ID.
     *
     * @param id the ID of the curve point to delete
     * @throws EntityNotFoundException if the curve point is not found
     */
    @Override
    public void delete(Long id) {
        Long nonNullId = Objects.requireNonNull(id);
        if (!curvePointRepository.existsById(nonNullId)) {
            throw new EntityNotFoundException("CurvePoint not found with id: " + id);
        }
        curvePointRepository.deleteById(nonNullId);
    }

    /**
     * Converts a CurvePoint entity to a CurvePointResponseDTO.
     *
     * @param curvePoint the CurvePoint entity to convert
     * @return the corresponding response DTO
     */
    @Override
    public CurvePointResponseDTO toResponseDTO(CurvePoint curvePoint) {
        return CurvePointResponseDTO.builder()
                .id(curvePoint.getId())
                .curveId(curvePoint.getCurveId())
                .asOfDate(curvePoint.getAsOfDate())
                .term(curvePoint.getTerm())
                .value(curvePoint.getValue())
                .build();
    }
}
