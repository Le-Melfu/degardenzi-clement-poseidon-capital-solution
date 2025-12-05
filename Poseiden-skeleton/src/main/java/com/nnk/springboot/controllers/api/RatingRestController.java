package com.nnk.springboot.controllers.api;

import com.nnk.springboot.dto.RatingCreateDTO;
import com.nnk.springboot.dto.RatingResponseDTO;
import com.nnk.springboot.dto.RatingUpdateDTO;
import com.nnk.springboot.services.interfaces.RatingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingRestController {

    private final RatingService ratingService;

    public RatingRestController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<RatingResponseDTO> create(@Valid @RequestBody RatingCreateDTO dto) {
        RatingResponseDTO created = ratingService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RatingResponseDTO> findById(@PathVariable Long id) {
        RatingResponseDTO rating = ratingService.findById(id);
        return ResponseEntity.ok(rating);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<RatingResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String moodysRating) {
        Page<RatingResponseDTO> ratings = ratingService.findAll(pageable, moodysRating);
        return ResponseEntity.ok(ratings);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<RatingResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RatingUpdateDTO dto) {
        RatingResponseDTO updated = ratingService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ratingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

