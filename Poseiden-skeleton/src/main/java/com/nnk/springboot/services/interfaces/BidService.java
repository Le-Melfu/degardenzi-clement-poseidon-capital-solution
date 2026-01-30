package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidlist.BidCreateDTO;
import com.nnk.springboot.dto.bidlist.BidResponseDTO;
import com.nnk.springboot.dto.bidlist.BidUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Service interface for BidList CRUD operations. */
public interface BidService {
    BidResponseDTO create(BidCreateDTO dto);
    BidResponseDTO findById(Long id);
    /** Returns a BidList ready for the update form (converted from DTO). */
    BidList getForUpdateForm(Long id);
    Page<BidResponseDTO> findAll(Pageable pageable, String account);
    BidResponseDTO update(Long id, BidUpdateDTO dto);
    void delete(Long id);
    BidResponseDTO toResponseDTO(BidList bid);
}

