package com.khaofit.khaofitservice.service;

import com.khaofit.khaofitservice.dto.request.FoodItemRegisterDto;
import org.springframework.http.ResponseEntity;

/**
 * this is a food item service class .
 *
 * @author kousik manik
 */
public interface FoodItemService {

  public ResponseEntity<?> registerFoodItem(Long restaurantId, Long categoryId, FoodItemRegisterDto dto);

  public ResponseEntity<?> getFoodItemDetails(Long foodItemId);

}
