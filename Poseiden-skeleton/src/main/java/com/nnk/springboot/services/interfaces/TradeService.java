package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeCreateDTO;
import com.nnk.springboot.dto.trade.TradeResponseDTO;
import com.nnk.springboot.dto.trade.TradeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/** Service interface for Trade CRUD operations. */
public interface TradeService {
    TradeResponseDTO create(TradeCreateDTO dto);
    TradeResponseDTO findById(Long id);
    /** Returns a Trade ready for the update form (converted from DTO). */
    Trade getForUpdateForm(Long id);
    Page<TradeResponseDTO> findAll(Pageable pageable, String account, LocalDateTime startDate, LocalDateTime endDate);
    TradeResponseDTO update(Long id, TradeUpdateDTO dto);
    void delete(Long id);
    TradeResponseDTO toResponseDTO(Trade trade);
}

