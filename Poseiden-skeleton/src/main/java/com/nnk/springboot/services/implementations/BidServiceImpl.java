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

/**
 * Service implementation for managing BidList entities.
 * Provides CRUD operations and business logic for bid management.
 */
@Service
@Transactional
public class BidServiceImpl implements BidService {

    private final BidListRepository bidListRepository;

    /**
     * Constructs a new BidServiceImpl with the given repository.
     *
     * @param bidListRepository the repository for BidList entities
     */
    public BidServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Creates a new bid from the provided DTO.
     *
     * @param dto the DTO containing bid creation data
     * @return the created bid as a response DTO
     */
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

    /**
     * Retrieves a bid by its ID.
     *
     * @param id the ID of the bid to retrieve
     * @return the bid as a response DTO
     * @throws EntityNotFoundException if the bid is not found
     */
    @Override
    @Transactional(readOnly = true)
    public BidResponseDTO findById(Long id) {
        BidList bid = Objects.requireNonNull(bidListRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("Bid not found with id: " + id)));
        return toResponseDTO(bid);
    }

    /**
     * Retrieves all bids with optional filtering by account.
     *
     * @param pageable pagination information
     * @param account  optional account filter (can be null)
     * @return a page of bid response DTOs
     */
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

    /**
     * Updates an existing bid with the provided data.
     *
     * @param id  the ID of the bid to update
     * @param dto the DTO containing update data
     * @return the updated bid as a response DTO
     * @throws EntityNotFoundException if the bid is not found
     */
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

    /**
     * Deletes a bid by its ID.
     *
     * @param id the ID of the bid to delete
     * @throws EntityNotFoundException if the bid is not found
     */
    @Override
    public void delete(Long id) {
        Long nonNullId = Objects.requireNonNull(id);
        if (!bidListRepository.existsById(nonNullId)) {
            throw new EntityNotFoundException("Bid not found with id: " + id);
        }
        bidListRepository.deleteById(nonNullId);
    }

    /**
     * Converts a BidList entity to a BidResponseDTO.
     *
     * @param bid the BidList entity to convert
     * @return the corresponding response DTO
     */
    @Override
    public BidResponseDTO toResponseDTO(BidList bid) {
        return BidResponseDTO.builder()
                .id(bid.getId())
                .account(bid.getAccount())
                .type(bid.getType())
                .bidQuantity(bid.getBidQuantity())
                .build();
    }
}
