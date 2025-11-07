package com.nnk.springboot.controllers.api;

import com.nnk.springboot.dto.CurvePointCreateDTO;
import com.nnk.springboot.dto.CurvePointResponseDTO;
import com.nnk.springboot.dto.CurvePointUpdateDTO;
import com.nnk.springboot.services.interfaces.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/curve-points")
public class CurvePointRestController {

    private final CurvePointService curvePointService;

    public CurvePointRestController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CurvePointResponseDTO> create(@Valid @RequestBody CurvePointCreateDTO dto) {
        CurvePointResponseDTO created = curvePointService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CurvePointResponseDTO> findById(@PathVariable Long id) {
        CurvePointResponseDTO curvePoint = curvePointService.findById(id);
        return ResponseEntity.ok(curvePoint);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<CurvePointResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "asOfDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Page<CurvePointResponseDTO> curvePoints = curvePointService.findAll(pageable, startDate, endDate);
        return ResponseEntity.ok(curvePoints);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CurvePointResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CurvePointUpdateDTO dto) {
        CurvePointResponseDTO updated = curvePointService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        curvePointService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

