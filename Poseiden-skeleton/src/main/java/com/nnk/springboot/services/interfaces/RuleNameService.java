package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameCreateDTO;
import com.nnk.springboot.dto.RuleNameResponseDTO;
import com.nnk.springboot.dto.RuleNameUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RuleNameService {
    RuleNameResponseDTO create(RuleNameCreateDTO dto);

    RuleNameResponseDTO findById(Long id);

    Page<RuleNameResponseDTO> findAll(Pageable pageable, String name);

    RuleNameResponseDTO update(Long id, RuleNameUpdateDTO dto);

    void delete(Long id);

    RuleNameResponseDTO toResponseDTO(RuleName ruleName);
}
