package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", BigDecimal.valueOf(10));

		// Save
		bid = bidListRepository.save(bid);
		Assertions.assertNotNull(bid.getId());
		Assertions.assertEquals(0, bid.getBidQuantity().compareTo(BigDecimal.valueOf(10)));

		// Update
		bid.setBidQuantity(BigDecimal.valueOf(20));
		bid = bidListRepository.save(bid);
		Assertions.assertEquals(0, bid.getBidQuantity().compareTo(BigDecimal.valueOf(20)));

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Long id = Objects.requireNonNull(bid.getId());
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		Assertions.assertFalse(bidList.isPresent());
	}
}
