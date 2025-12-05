package com.nnk.springboot.controllers.api;

import com.nnk.springboot.dto.RuleNameCreateDTO;
import com.nnk.springboot.dto.RuleNameResponseDTO;
import com.nnk.springboot.dto.RuleNameUpdateDTO;
import com.nnk.springboot.services.interfaces.RuleNameService;
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
@RequestMapping("/api/rules")
public class RuleNameRestController {

    private final RuleNameService ruleNameService;

    public RuleNameRestController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<RuleNameResponseDTO> create(@Valid @RequestBody RuleNameCreateDTO dto) {
        RuleNameResponseDTO created = ruleNameService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RuleNameResponseDTO> findById(@PathVariable Long id) {
        RuleNameResponseDTO ruleName = ruleNameService.findById(id);
        return ResponseEntity.ok(ruleName);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<RuleNameResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String name) {
        Page<RuleNameResponseDTO> ruleNames = ruleNameService.findAll(pageable, name);
        return ResponseEntity.ok(ruleNames);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<RuleNameResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RuleNameUpdateDTO dto) {
        RuleNameResponseDTO updated = ruleNameService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ruleNameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

