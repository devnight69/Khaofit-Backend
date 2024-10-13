package com.khaofit.khaofitservice.dto.response;

import com.khaofit.khaofitservice.enums.FoodType;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this is a food item response dto class .
 *
 * @author kousik manik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemResponseDto {

  private Long foodId;

  private String name;

  private String description;

  private FoodType foodType;

  private Double price;

  private RestaurantFoodItemResponseDto restaurantDetails;

  private CategoryFoodItemResponseDto categoryDetails;

  private List<FoodImageFoodItemResponseDto> foodImageDetails;

  private List<FoodItemRatingResponseDto> ratingDetails;

  private boolean active;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;

  /**
   * this is a constructor .
   */
  public FoodItemResponseDto(Long foodId,
                             String name,
                             String description,
                             FoodType foodType,
                             Double price,
                             boolean active,
                             OffsetDateTime createdAt,
                             OffsetDateTime updatedAt,
                             RestaurantFoodItemResponseDto restaurantFoodItemResponseDto,
                             CategoryFoodItemResponseDto categoryFoodItemResponseDto,
                             List<FoodImageFoodItemResponseDto> foodImageDetails,
                             List<FoodItemRatingResponseDto> ratingDetails) {
    this.foodId = foodId;
    this.name = name;
    this.description = description;
    this.foodType = foodType;
    this.price = price;
    this.active = active;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.restaurantDetails = restaurantFoodItemResponseDto;
    this.categoryDetails = categoryFoodItemResponseDto;
    this.foodImageDetails = foodImageDetails;
    this.ratingDetails = ratingDetails;
  }

}
