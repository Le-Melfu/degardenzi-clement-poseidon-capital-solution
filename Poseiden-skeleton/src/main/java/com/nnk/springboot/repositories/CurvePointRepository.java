package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface CurvePointRepository extends JpaRepository<CurvePoint, Long>, JpaSpecificationExecutor<CurvePoint> {
    List<CurvePoint> findByAsOfDateBetween(LocalDateTime start, LocalDateTime end);
}
