package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.Category;
import com.khaofit.khaofitservice.model.FoodItem;
import com.khaofit.khaofitservice.model.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * this is a food item repository class .
 *
 * @author kousik manik
 */
@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

  Optional<FoodItem> findByRestaurantAndCategoryAndName(Restaurant restaurant, Category category, String name);

  boolean existsByRestaurantAndCategoryAndName(Restaurant restaurant, Category category, String name);

  @Query("SELECT f FROM FoodItem f JOIN FETCH f.restaurant JOIN FETCH f.category WHERE f.foodId = :foodItemId")
  Optional<FoodItem> findByIdWithDetails(@Param("foodItemId") Long foodItemId);

  List<FoodItem> findByRestaurant_RestaurantId(Long restaurantId);

}
