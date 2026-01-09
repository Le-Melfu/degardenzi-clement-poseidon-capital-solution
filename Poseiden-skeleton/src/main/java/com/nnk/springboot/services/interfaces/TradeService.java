package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeCreateDTO;
import com.nnk.springboot.dto.TradeResponseDTO;
import com.nnk.springboot.dto.TradeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TradeService {
    TradeResponseDTO create(TradeCreateDTO dto);
    TradeResponseDTO findById(Long id);
    Page<TradeResponseDTO> findAll(Pageable pageable, String account, LocalDateTime startDate, LocalDateTime endDate);
    TradeResponseDTO update(Long id, TradeUpdateDTO dto);
    void delete(Long id);
    TradeResponseDTO toResponseDTO(Trade trade);
}

