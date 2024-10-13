package com.khaofit.khaofitservice.service;

import com.khaofit.khaofitservice.dto.request.FoodItemRegisterDto;
import com.khaofit.khaofitservice.enums.FoodType;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

/**
 * this is a food item service class .
 *
 * @author kousik manik
 */
public interface FoodItemService {

  public ResponseEntity<?> registerFoodItem(Long restaurantId, Long categoryId, FoodItemRegisterDto dto);

  public ResponseEntity<?> getFoodItemDetails(Long foodItemId);

  public ResponseEntity<?> getFoodItemDetailsByRestaurantId(Long restaurantId);

  public ResponseEntity<?> getFoodItemDetailsByCategory(Long categoryId);

  public ResponseEntity<?> getFoodItemDetailsByFoodType(FoodType foodType);

}
