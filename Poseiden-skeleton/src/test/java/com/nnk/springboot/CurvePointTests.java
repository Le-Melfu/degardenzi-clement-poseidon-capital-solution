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
	public void testCreate() {
		CurvePoint curvePoint = new CurvePoint(BigDecimal.valueOf(10), BigDecimal.valueOf(30));
		curvePoint = curvePointRepository.save(curvePoint);
		Assertions.assertNotNull(curvePoint.getId());
		Assertions.assertEquals(0, curvePoint.getTerm().compareTo(BigDecimal.valueOf(10)));
		curvePointRepository.delete(curvePoint);
	}

	@Test
	public void testRead() {
		CurvePoint curvePoint = new CurvePoint(BigDecimal.valueOf(10), BigDecimal.valueOf(30));
		curvePoint = curvePointRepository.save(curvePoint);
		List<CurvePoint> listResult = curvePointRepository.findAll();
		Assertions.assertTrue(listResult.size() > 0);
		curvePointRepository.delete(curvePoint);
	}

	@Test
	public void testUpdate() {
		CurvePoint curvePoint = new CurvePoint(BigDecimal.valueOf(10), BigDecimal.valueOf(30));
		curvePoint = curvePointRepository.save(curvePoint);
		curvePoint.setTerm(BigDecimal.valueOf(20));
		curvePoint = curvePointRepository.save(curvePoint);
		Assertions.assertEquals(0, curvePoint.getTerm().compareTo(BigDecimal.valueOf(20)));
		curvePointRepository.delete(curvePoint);
	}

	@Test
	public void testDelete() {
		CurvePoint curvePoint = new CurvePoint(BigDecimal.valueOf(10), BigDecimal.valueOf(30));
		curvePoint = curvePointRepository.save(curvePoint);
		Long id = Objects.requireNonNull(curvePoint.getId());
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		Assertions.assertFalse(curvePointList.isPresent());
	}

}
