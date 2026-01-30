package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.rulename.RuleNameCreateDTO;
import com.nnk.springboot.dto.rulename.RuleNameResponseDTO;
import com.nnk.springboot.dto.rulename.RuleNameUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Service interface for RuleName CRUD operations. */
public interface RuleNameService {
    RuleNameResponseDTO create(RuleNameCreateDTO dto);
    RuleNameResponseDTO findById(Long id);
    /** Returns a RuleName ready for the update form (converted from DTO). */
    RuleName getForUpdateForm(Long id);
    Page<RuleNameResponseDTO> findAll(Pageable pageable, String name);
    RuleNameResponseDTO update(Long id, RuleNameUpdateDTO dto);
    void delete(Long id);
    RuleNameResponseDTO toResponseDTO(RuleName ruleName);
}
