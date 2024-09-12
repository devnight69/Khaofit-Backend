package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.Restaurant;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * this is a repository class for Restaurant .
 *
 * @author kousik manik
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

  Optional<Restaurant> findByMobileNumber(String mobileNumber);

  @Query("SELECT r FROM Restaurant r JOIN FETCH r.foodItemList WHERE r.restaurantId = :restaurantId")
  Optional<Restaurant> findByIdWithFoodItems(@Param("restaurantId") Long restaurantId);

  @Modifying
  @Transactional
  @Query("UPDATE Restaurant r SET r.active = false WHERE r.restaurantId = :restaurantId")
  int deactivateRestaurant(@Param("restaurantId") Long restaurantId);

}
