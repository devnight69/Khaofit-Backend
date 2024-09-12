package com.khaofit.khaofitservice.dto.response;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this is a response dto for food item with restaurant details .
 *
 * @author kousik manik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemRestaurantResponseDto {

  private Long foodId;

  private String name;

  private String description;

  private Double price;

  private CategoryFoodItemResponseDto categoryDetails;

  private List<FoodImageFoodItemResponseDto> foodImageDetails;

  private List<FoodItemRatingResponseDto> ratingDetails;

  private boolean active;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;

  /**
   * this is a constructor .
   */
  public FoodItemRestaurantResponseDto(Long foodId,
                             String name,
                             String description,
                             Double price,
                             boolean active,
                             OffsetDateTime createdAt,
                             OffsetDateTime updatedAt,
                             CategoryFoodItemResponseDto categoryFoodItemResponseDto,
                             List<FoodImageFoodItemResponseDto> foodImageDetails,
                             List<FoodItemRatingResponseDto> ratingDetails) {
    this.foodId = foodId;
    this.name = name;
    this.description = description;
    this.price = price;
    this.active = active;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.categoryDetails = categoryFoodItemResponseDto;
    this.foodImageDetails = foodImageDetails;
    this.ratingDetails = ratingDetails;
  }

}
