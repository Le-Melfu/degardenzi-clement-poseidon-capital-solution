package com.nnk.springboot.services.implementations;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameCreateDTO;
import com.nnk.springboot.dto.RuleNameResponseDTO;
import com.nnk.springboot.dto.RuleNameUpdateDTO;
import com.nnk.springboot.exception.EntityNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.interfaces.RuleNameService;
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
public class RuleNameServiceImpl implements RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    @SuppressWarnings("null")
    public RuleNameResponseDTO create(RuleNameCreateDTO dto) {
        if (ruleNameRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Rule name already exists: " + dto.getName());
        }

        RuleName ruleName = RuleName.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .json(dto.getJson())
                .template(dto.getTemplate())
                .sqlStr(dto.getSqlStr())
                .sqlPart(dto.getSqlPart())
                .enabled(dto.getEnabled() != null ? dto.getEnabled() : true)
                .build();

        RuleName saved = Objects.requireNonNull(ruleNameRepository.save(ruleName));
        return toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RuleNameResponseDTO findById(Long id) {
        RuleName ruleName = Objects.requireNonNull(ruleNameRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("RuleName not found with id: " + id)));
        return toResponseDTO(ruleName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RuleNameResponseDTO> findAll(Pageable pageable, String name) {
        Objects.requireNonNull(pageable);
        Specification<RuleName> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return ruleNameRepository.findAll(spec, pageable)
                .map(this::toResponseDTO);
    }

    @Override
    public RuleNameResponseDTO update(Long id, RuleNameUpdateDTO dto) {
        RuleName ruleName = Objects.requireNonNull(ruleNameRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("RuleName not found with id: " + id)));

        if (dto.getName() != null && !dto.getName().equals(ruleName.getName())) {
            if (ruleNameRepository.existsByName(dto.getName())) {
                throw new IllegalArgumentException("Rule name already exists: " + dto.getName());
            }
            ruleName.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            ruleName.setDescription(dto.getDescription());
        }
        if (dto.getJson() != null) {
            ruleName.setJson(dto.getJson());
        }
        if (dto.getTemplate() != null) {
            ruleName.setTemplate(dto.getTemplate());
        }
        if (dto.getSqlStr() != null) {
            ruleName.setSqlStr(dto.getSqlStr());
        }
        if (dto.getSqlPart() != null) {
            ruleName.setSqlPart(dto.getSqlPart());
        }
        if (dto.getEnabled() != null) {
            ruleName.setEnabled(dto.getEnabled());
        }

        RuleName updated = Objects.requireNonNull(ruleNameRepository.save(ruleName));
        return toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Long nonNullId = Objects.requireNonNull(id);
        if (!ruleNameRepository.existsById(nonNullId)) {
            throw new EntityNotFoundException("RuleName not found with id: " + id);
        }
        ruleNameRepository.deleteById(nonNullId);
    }

    @Override
    public RuleNameResponseDTO toResponseDTO(RuleName ruleName) {
        return RuleNameResponseDTO.builder()
                .id(ruleName.getId())
                .name(ruleName.getName())
                .description(ruleName.getDescription())
                .json(ruleName.getJson())
                .template(ruleName.getTemplate())
                .sqlStr(ruleName.getSqlStr())
                .sqlPart(ruleName.getSqlPart())
                .enabled(ruleName.getEnabled())
                .build();
    }
}

