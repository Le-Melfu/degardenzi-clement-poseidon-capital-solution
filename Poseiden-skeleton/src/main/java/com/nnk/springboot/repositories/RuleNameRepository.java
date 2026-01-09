package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RuleNameRepository extends JpaRepository<RuleName, Long>, JpaSpecificationExecutor<RuleName> {
    Optional<RuleName> findByName(String name);
    boolean existsByName(String name);
}
