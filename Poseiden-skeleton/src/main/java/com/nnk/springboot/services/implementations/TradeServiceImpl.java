package com.nnk.springboot.services.implementations;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeCreateDTO;
import com.nnk.springboot.dto.TradeResponseDTO;
import com.nnk.springboot.dto.TradeUpdateDTO;
import com.nnk.springboot.exception.EntityNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.interfaces.TradeService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service implementation for managing Trade entities.
 * Provides CRUD operations and business logic for trade management.
 */
@Service
@Transactional
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    /**
     * Constructs a new TradeServiceImpl with the given repository.
     *
     * @param tradeRepository the repository for Trade entities
     */
    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Creates a new trade from the provided DTO.
     *
     * @param dto the DTO containing trade creation data
     * @return the created trade as a response DTO
     */
    @Override
    @SuppressWarnings("null")
    public TradeResponseDTO create(TradeCreateDTO dto) {
        Trade trade = Trade.builder()
                .account(dto.getAccount())
                .type(dto.getType())
                .buyQuantity(dto.getBuyQuantity())
                .sellQuantity(dto.getSellQuantity())
                .tradeDate(dto.getTradeDate())
                .build();

        Trade saved = Objects.requireNonNull(tradeRepository.save(trade));
        return toResponseDTO(saved);
    }

    /**
     * Retrieves a trade by its ID.
     *
     * @param id the ID of the trade to retrieve
     * @return the trade as a response DTO
     * @throws EntityNotFoundException if the trade is not found
     */
    @Override
    @Transactional(readOnly = true)
    public TradeResponseDTO findById(Long id) {
        Trade trade = Objects.requireNonNull(tradeRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id)));
        return toResponseDTO(trade);
    }

    /**
     * Retrieves all trades with optional filtering by account and date range.
     *
     * @param pageable pagination information
     * @param account optional account filter (can be null)
     * @param startDate optional start date for filtering (can be null)
     * @param endDate optional end date for filtering (can be null)
     * @return a page of trade response DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TradeResponseDTO> findAll(Pageable pageable, String account, LocalDateTime startDate, LocalDateTime endDate) {
        Objects.requireNonNull(pageable);
        Specification<Trade> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (account != null && !account.isEmpty()) {
                predicates.add(cb.equal(root.get("account"), account));
            }
            if (startDate != null && endDate != null) {
                predicates.add(cb.between(root.get("tradeDate"), startDate, endDate));
            } else if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("tradeDate"), startDate));
            } else if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("tradeDate"), endDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return tradeRepository.findAll(spec, pageable)
                .map(this::toResponseDTO);
    }

    /**
     * Updates an existing trade with the provided data.
     *
     * @param id the ID of the trade to update
     * @param dto the DTO containing update data
     * @return the updated trade as a response DTO
     * @throws EntityNotFoundException if the trade is not found
     */
    @Override
    public TradeResponseDTO update(Long id, TradeUpdateDTO dto) {
        Trade trade = Objects.requireNonNull(tradeRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id)));

        if (dto.getAccount() != null) {
            trade.setAccount(dto.getAccount());
        }
        if (dto.getType() != null) {
            trade.setType(dto.getType());
        }
        if (dto.getBuyQuantity() != null) {
            trade.setBuyQuantity(dto.getBuyQuantity());
        }
        if (dto.getSellQuantity() != null) {
            trade.setSellQuantity(dto.getSellQuantity());
        }
        if (dto.getTradeDate() != null) {
            trade.setTradeDate(dto.getTradeDate());
        }

        Trade updated = Objects.requireNonNull(tradeRepository.save(trade));
        return toResponseDTO(updated);
    }

    /**
     * Deletes a trade by its ID.
     *
     * @param id the ID of the trade to delete
     * @throws EntityNotFoundException if the trade is not found
     */
    @Override
    public void delete(Long id) {
        Long nonNullId = Objects.requireNonNull(id);
        if (!tradeRepository.existsById(nonNullId)) {
            throw new EntityNotFoundException("Trade not found with id: " + id);
        }
        tradeRepository.deleteById(nonNullId);
    }

    /**
     * Converts a Trade entity to a TradeResponseDTO.
     *
     * @param trade the Trade entity to convert
     * @return the corresponding response DTO
     */
    @Override
    public TradeResponseDTO toResponseDTO(Trade trade) {
        return TradeResponseDTO.builder()
                .id(trade.getId())
                .account(trade.getAccount())
                .type(trade.getType())
                .buyQuantity(trade.getBuyQuantity())
                .sellQuantity(trade.getSellQuantity())
                .tradeDate(trade.getTradeDate())
                .build();
    }
}

