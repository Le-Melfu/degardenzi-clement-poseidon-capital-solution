package com.nnk.springboot.services.implementations;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidCreateDTO;
import com.nnk.springboot.dto.BidResponseDTO;
import com.nnk.springboot.dto.BidUpdateDTO;
import com.nnk.springboot.exception.EntityNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.interfaces.BidService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BidServiceImpl implements BidService {

    private final BidListRepository bidListRepository;

    public BidServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    @SuppressWarnings("null")
    public BidResponseDTO create(BidCreateDTO dto) {
        BidList bid = BidList.builder()
                .account(dto.getAccount())
                .type(dto.getType())
                .bidQuantity(dto.getBidQuantity())
                .build();

        BidList saved = Objects.requireNonNull(bidListRepository.save(bid));
        return toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BidResponseDTO findById(Long id) {
        BidList bid = Objects.requireNonNull(bidListRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("Bid not found with id: " + id)));
        return toResponseDTO(bid);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BidResponseDTO> findAll(Pageable pageable, String account) {
        Objects.requireNonNull(pageable);
        Specification<BidList> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (account != null && !account.isEmpty()) {
                predicates.add(cb.equal(root.get("account"), account));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return bidListRepository.findAll(spec, pageable)
                .map(this::toResponseDTO);
    }

    @Override
    public BidResponseDTO update(Long id, BidUpdateDTO dto) {
        BidList bid = Objects.requireNonNull(bidListRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("Bid not found with id: " + id)));

        if (dto.getAccount() != null) {
            bid.setAccount(dto.getAccount());
        }
        if (dto.getType() != null) {
            bid.setType(dto.getType());
        }
        if (dto.getBidQuantity() != null) {
            bid.setBidQuantity(dto.getBidQuantity());
        }

        BidList updated = Objects.requireNonNull(bidListRepository.save(bid));
        return toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Long nonNullId = Objects.requireNonNull(id);
        if (!bidListRepository.existsById(nonNullId)) {
            throw new EntityNotFoundException("Bid not found with id: " + id);
        }
        bidListRepository.deleteById(nonNullId);
    }

    @Override
    public BidResponseDTO toResponseDTO(BidList bid) {
        return BidResponseDTO.builder()
                .id(bid.getId())
                .account(bid.getAccount())
                .type(bid.getType())
                .bidQuantity(bid.getBidQuantity())
                .createdAt(bid.getCreatedAt())
                .updatedAt(bid.getUpdatedAt())
                .build();
    }
}
