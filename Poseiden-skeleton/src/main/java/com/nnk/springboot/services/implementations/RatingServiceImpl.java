package com.nnk.springboot.services.implementations;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingCreateDTO;
import com.nnk.springboot.dto.RatingResponseDTO;
import com.nnk.springboot.dto.RatingUpdateDTO;
import com.nnk.springboot.exception.EntityNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.interfaces.RatingService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    @SuppressWarnings("null")
    public RatingResponseDTO create(RatingCreateDTO dto) {
        Rating rating = Rating.builder()
                .moodysRating(dto.getMoodysRating())
                .sandPRating(dto.getSandPRating())
                .fitchRating(dto.getFitchRating())
                .orderNumber(dto.getOrderNumber())
                .build();

        Rating saved = Objects.requireNonNull(ratingRepository.save(rating));
        return toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RatingResponseDTO findById(Long id) {
        Rating rating = Objects.requireNonNull(ratingRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("Rating not found with id: " + id)));
        return toResponseDTO(rating);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RatingResponseDTO> findAll(Pageable pageable, String moodysRating) {
        Objects.requireNonNull(pageable);
        Specification<Rating> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (moodysRating != null && !moodysRating.isEmpty()) {
                predicates.add(cb.equal(root.get("moodysRating"), moodysRating));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return ratingRepository.findAll(spec, pageable)
                .map(this::toResponseDTO);
    }

    @Override
    public RatingResponseDTO update(Long id, RatingUpdateDTO dto) {
        Rating rating = Objects.requireNonNull(ratingRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("Rating not found with id: " + id)));

        if (dto.getMoodysRating() != null) {
            rating.setMoodysRating(dto.getMoodysRating());
        }
        if (dto.getSandPRating() != null) {
            rating.setSandPRating(dto.getSandPRating());
        }
        if (dto.getFitchRating() != null) {
            rating.setFitchRating(dto.getFitchRating());
        }
        if (dto.getOrderNumber() != null) {
            rating.setOrderNumber(dto.getOrderNumber());
        }

        Rating updated = Objects.requireNonNull(ratingRepository.save(rating));
        return toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Long nonNullId = Objects.requireNonNull(id);
        if (!ratingRepository.existsById(nonNullId)) {
            throw new EntityNotFoundException("Rating not found with id: " + id);
        }
        ratingRepository.deleteById(nonNullId);
    }

    @Override
    public RatingResponseDTO toResponseDTO(Rating rating) {
        return RatingResponseDTO.builder()
                .id(rating.getId())
                .moodysRating(rating.getMoodysRating())
                .sandPRating(rating.getSandPRating())
                .fitchRating(rating.getFitchRating())
                .orderNumber(rating.getOrderNumber())
                .createdAt(rating.getCreatedAt())
                .updatedAt(rating.getUpdatedAt())
                .build();
    }
}

