package com.nnk.springboot.controllers.api;

import com.nnk.springboot.dto.TradeCreateDTO;
import com.nnk.springboot.dto.TradeResponseDTO;
import com.nnk.springboot.dto.TradeUpdateDTO;
import com.nnk.springboot.services.interfaces.TradeService;
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
@RequestMapping("/api/trades")
public class TradeRestController {

    private final TradeService tradeService;

    public TradeRestController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TradeResponseDTO> create(@Valid @RequestBody TradeCreateDTO dto) {
        TradeResponseDTO created = tradeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TradeResponseDTO> findById(@PathVariable Long id) {
        TradeResponseDTO trade = tradeService.findById(id);
        return ResponseEntity.ok(trade);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<TradeResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String account,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Page<TradeResponseDTO> trades = tradeService.findAll(pageable, account, startDate, endDate);
        return ResponseEntity.ok(trades);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TradeResponseDTO> update(@PathVariable Long id, @Valid @RequestBody TradeUpdateDTO dto) {
        TradeResponseDTO updated = tradeService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tradeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

