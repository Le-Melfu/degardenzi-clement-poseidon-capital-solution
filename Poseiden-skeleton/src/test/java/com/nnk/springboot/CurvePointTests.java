package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(BigDecimal.valueOf(10), BigDecimal.valueOf(30));

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		Assertions.assertNotNull(curvePoint.getId());
		Assertions.assertEquals(0, curvePoint.getTerm().compareTo(BigDecimal.valueOf(10)));

		// Update
		curvePoint.setTerm(BigDecimal.valueOf(20));
		curvePoint = curvePointRepository.save(curvePoint);
		Assertions.assertEquals(0, curvePoint.getTerm().compareTo(BigDecimal.valueOf(20)));

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Long id = Objects.requireNonNull(curvePoint.getId());
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		Assertions.assertFalse(curvePointList.isPresent());
	}

}
