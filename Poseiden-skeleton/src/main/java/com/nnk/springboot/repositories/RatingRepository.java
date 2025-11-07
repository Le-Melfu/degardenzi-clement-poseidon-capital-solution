package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long>, JpaSpecificationExecutor<Rating> {
    List<Rating> findByMoodysRating(String moodysRating);
    List<Rating> findBySandPRating(String sandPRating);
    List<Rating> findByFitchRating(String fitchRating);
}
