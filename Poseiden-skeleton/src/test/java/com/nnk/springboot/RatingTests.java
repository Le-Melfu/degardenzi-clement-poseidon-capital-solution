package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

    @Test
    public void testCreate() {
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating = ratingRepository.save(rating);
        Assertions.assertNotNull(rating.getId());
        Assertions.assertEquals(10, rating.getOrderNumber());
        ratingRepository.delete(rating);
    }

	@Test
	public void testRead() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		rating = ratingRepository.save(rating);
		List<Rating> listResult = ratingRepository.findAll();
		Assertions.assertTrue(listResult.size() > 0);
		ratingRepository.delete(rating);
	}

    @Test
    public void testUpdate() {
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating = ratingRepository.save(rating);
        rating.setOrderNumber(20);
        rating = ratingRepository.save(rating);
        Assertions.assertEquals(20, rating.getOrderNumber());
        ratingRepository.delete(rating);
    }

	@Test
	public void testDelete() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		rating = ratingRepository.save(rating);
		Long id = Objects.requireNonNull(rating.getId());
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		Assertions.assertFalse(ratingList.isPresent());
	}
}
