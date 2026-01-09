package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void testCreate() {
		Trade trade = new Trade("Trade Account", "Type");
		trade = tradeRepository.save(trade);
		Assertions.assertNotNull(trade.getId());
		Assertions.assertTrue(trade.getAccount().equals("Trade Account"));
		tradeRepository.delete(trade);
	}

	@Test
	public void testRead() {
		Trade trade = new Trade("Trade Account Read", "Type");
		trade = tradeRepository.save(trade);
		List<Trade> listResult = tradeRepository.findAll();
		Assertions.assertTrue(listResult.size() > 0);
		tradeRepository.delete(trade);
	}

	@Test
	public void testUpdate() {
		Trade trade = new Trade("Trade Account", "Type");
		trade = tradeRepository.save(trade);
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		Assertions.assertTrue(trade.getAccount().equals("Trade Account Update"));
		tradeRepository.delete(trade);
	}

	@Test
	public void testDelete() {
		Trade trade = new Trade("Trade Account", "Type");
		trade = tradeRepository.save(trade);
		Long id = Objects.requireNonNull(trade.getId());
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		Assertions.assertFalse(tradeList.isPresent());
	}
}
