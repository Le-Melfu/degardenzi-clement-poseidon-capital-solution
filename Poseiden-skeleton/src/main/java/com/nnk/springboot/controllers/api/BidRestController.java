package com.nnk.springboot.controllers.api;

import com.nnk.springboot.dto.BidCreateDTO;
import com.nnk.springboot.dto.BidResponseDTO;
import com.nnk.springboot.dto.BidUpdateDTO;
import com.nnk.springboot.services.interfaces.BidService;
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
@RequestMapping("/api/bids")
public class BidRestController {

    private final BidService bidService;

    public BidRestController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<BidResponseDTO> create(@Valid @RequestBody BidCreateDTO dto) {
        BidResponseDTO created = bidService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BidResponseDTO> findById(@PathVariable Long id) {
        BidResponseDTO bid = bidService.findById(id);
        return ResponseEntity.ok(bid);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<BidResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String account) {
        Page<BidResponseDTO> bids = bidService.findAll(pageable, account);
        return ResponseEntity.ok(bids);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<BidResponseDTO> update(@PathVariable Long id, @Valid @RequestBody BidUpdateDTO dto) {
        BidResponseDTO updated = bidService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bidService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

