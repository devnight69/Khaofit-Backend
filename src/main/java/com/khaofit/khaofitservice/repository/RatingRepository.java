package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is a repository class for rating .
 *
 * @author kousik manik
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
