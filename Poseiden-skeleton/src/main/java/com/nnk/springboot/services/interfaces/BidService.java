package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidCreateDTO;
import com.nnk.springboot.dto.BidResponseDTO;
import com.nnk.springboot.dto.BidUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BidService {
    BidResponseDTO create(BidCreateDTO dto);
    BidResponseDTO findById(Long id);
    Page<BidResponseDTO> findAll(Pageable pageable, String account);
    BidResponseDTO update(Long id, BidUpdateDTO dto);
    void delete(Long id);
    BidResponseDTO toResponseDTO(BidList bid);
}

