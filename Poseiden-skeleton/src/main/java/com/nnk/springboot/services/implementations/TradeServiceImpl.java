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

@Service
@Transactional
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

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

    @Override
    @Transactional(readOnly = true)
    public TradeResponseDTO findById(Long id) {
        Trade trade = Objects.requireNonNull(tradeRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id)));
        return toResponseDTO(trade);
    }

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

    @Override
    public void delete(Long id) {
        Long nonNullId = Objects.requireNonNull(id);
        if (!tradeRepository.existsById(nonNullId)) {
            throw new EntityNotFoundException("Trade not found with id: " + id);
        }
        tradeRepository.deleteById(nonNullId);
    }

    @Override
    public TradeResponseDTO toResponseDTO(Trade trade) {
        return TradeResponseDTO.builder()
                .id(trade.getId())
                .account(trade.getAccount())
                .type(trade.getType())
                .buyQuantity(trade.getBuyQuantity())
                .sellQuantity(trade.getSellQuantity())
                .tradeDate(trade.getTradeDate())
                .createdAt(trade.getCreatedAt())
                .updatedAt(trade.getUpdatedAt())
                .build();
    }
}

