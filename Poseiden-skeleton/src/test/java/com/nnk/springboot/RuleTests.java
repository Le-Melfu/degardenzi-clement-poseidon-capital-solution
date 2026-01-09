package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	public void testCreate() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		rule = ruleNameRepository.save(rule);
		Assertions.assertNotNull(rule.getId());
		Assertions.assertEquals("Rule Name", rule.getName());
		ruleNameRepository.delete(rule);
	}

	@Test
	public void testRead() {
		RuleName rule = new RuleName("Rule Name Read", "Description", "Json", "Template", "SQL", "SQL Part");
		rule = ruleNameRepository.save(rule);
		List<RuleName> listResult = ruleNameRepository.findAll();
		Assertions.assertTrue(listResult.size() > 0);
		ruleNameRepository.delete(rule);
	}

	@Test
	public void testUpdate() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		rule = ruleNameRepository.save(rule);
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		Assertions.assertEquals("Rule Name Update", rule.getName());
		ruleNameRepository.delete(rule);
	}

	@Test
	public void testDelete() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		rule = ruleNameRepository.save(rule);
		Long id = Objects.requireNonNull(rule.getId());
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		Assertions.assertFalse(ruleList.isPresent());
	}
}
