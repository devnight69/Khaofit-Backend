package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.dto.request.FoodItemRegisterDto;
import com.khaofit.khaofitservice.enums.FoodType;
import com.khaofit.khaofitservice.service.FoodItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a controller class for food item .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/food-item")
public class FoodItemController {

  @Autowired
  private FoodItemService foodItemService;

  @PostMapping("/register")
  public ResponseEntity<?> registerFoodItem(@Valid @RequestParam(name = "restaurantId")
                                            @NotNull(message = "restaurantId is required") Long restaurantId,
                                            @Valid @RequestParam(name = "categoryId")
                                            @NotNull(message = "categoryId is required") Long categoryId,
                                            @Valid @RequestBody FoodItemRegisterDto dto) {
    return foodItemService.registerFoodItem(restaurantId, categoryId, dto);
  }

  @GetMapping("/detailsById")
  public ResponseEntity<?> getFoodItemDetails(@Valid @RequestParam(name = "foodItemId")
                                              @NotNull(message = "foodItemId is required") Long foodItemId) {
    return foodItemService.getFoodItemDetails(foodItemId);
  }

  @GetMapping("/details/byRestaurantId")
  public ResponseEntity<?> getFoodItemDetailsByRestaurantId(@Valid @RequestParam(name = "restaurantId")
                                                            @NotNull(message = "restaurantId is required")
                                                            Long restaurantId) {
    return foodItemService.getFoodItemDetailsByRestaurantId(restaurantId);
  }

  @GetMapping("/details/byCategoryId")
  public ResponseEntity<?> getFoodItemDetailsByCategory(@Valid @RequestParam(name = "categoryId")
                                                        @NotNull(message = "categoryId is required")
                                                        Long categoryId) {
    return foodItemService.getFoodItemDetailsByCategory(categoryId);
  }

  @GetMapping("/details/by-food-type")
  public ResponseEntity<?> getFoodItemDetailsByFoodType(@Valid @RequestParam(name = "foodType")
                                                        @NotNull(message = "foodType is required")
                                                        FoodType foodType) {
    return foodItemService.getFoodItemDetailsByFoodType(foodType);
  }


}
