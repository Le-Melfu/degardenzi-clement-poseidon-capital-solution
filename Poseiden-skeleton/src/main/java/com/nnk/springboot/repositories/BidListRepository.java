package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BidListRepository extends JpaRepository<BidList, Long>, JpaSpecificationExecutor<BidList> {
    List<BidList> findByAccount(String account);
}
