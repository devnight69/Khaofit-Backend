package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is a repository class for food image .
 *
 * @author kousik manik
 */
@Repository
public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {

}
