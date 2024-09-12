package com.khaofit.khaofitservice.converter;

import com.khaofit.khaofitservice.dto.response.CategoryFoodItemResponseDto;
import com.khaofit.khaofitservice.dto.response.FoodImageFoodItemResponseDto;
import com.khaofit.khaofitservice.dto.response.FoodItemRatingResponseDto;
import com.khaofit.khaofitservice.dto.response.FoodItemRestaurantResponseDto;
import com.khaofit.khaofitservice.dto.response.RestaurantResponseDto;
import com.khaofit.khaofitservice.model.Category;
import com.khaofit.khaofitservice.model.FoodImage;
import com.khaofit.khaofitservice.model.FoodItem;
import com.khaofit.khaofitservice.model.Rating;
import com.khaofit.khaofitservice.model.Restaurant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * this is a converter class for restaurant details .
 *
 * @author kousik manik
 */
@Component
public class RestaurantAndFoodItemConverter {

  /**
   * this is a converter method for Restaurant to RestaurantResponseDto .
   *
   * @param restaurant @{@link Restaurant}
   * @return @{@link RestaurantResponseDto}
   */
  public RestaurantResponseDto convertToRestaurantResponseDto(Restaurant restaurant) {
    return new RestaurantResponseDto(
        restaurant.getRestaurantId(),
        restaurant.getUlId(),
        restaurant.getName(),
        restaurant.getLocation(),
        restaurant.getContactInfo(),
        restaurant.getMobileNumber(),
        restaurant.getRating(),
        restaurant.getOpeningHours(),
        restaurant.isActive(),
        restaurant.getOpenTime(),
        restaurant.getCloseTime(),
        restaurant.getCreatedAt(),
        restaurant.getUpdatedAt(),
        convertFoodItemsToFoodItemRestaurantResponseDtos(restaurant.getFoodItemList())
    );
  }

  /**
   * this is a converter method for FoodItem to FoodItemRestaurantResponseDto .
   *
   * @param foodItem @{@link FoodItem}
   * @return @{@link FoodItemRestaurantResponseDto}
   */
  public FoodItemRestaurantResponseDto convertToFoodItemRestaurantResponseDto(FoodItem foodItem) {
    return new FoodItemRestaurantResponseDto(
        foodItem.getFoodId(),
        foodItem.getName(),
        foodItem.getDescription(),
        foodItem.getPrice(),
        foodItem.isActive(),
        foodItem.getCreatedAt(),
        foodItem.getUpdatedAt(),
        convertCategoryToCategoryFoodItemResponseDto(foodItem.getCategory()),
        convertFoodImagesToFoodImageFoodItemResponseDtos(foodItem.getImages()),
        convertRatingsToFoodItemRatingResponseDtos(foodItem.getRatings())
    );
  }

  /**
   * convertFoodItemsToFoodItemRestaurantResponseDtos .
   *
   * @param foodItems @{@link List}
   * @return @{@link List}
   */
  private List<FoodItemRestaurantResponseDto> convertFoodItemsToFoodItemRestaurantResponseDtos(List<FoodItem> foodItems) {
    return foodItems.stream()
        .map(this::convertToFoodItemRestaurantResponseDto)
        .collect(Collectors.toList());
  }

  /**
   * this is a converter method .
   *
   * @param category @{@link Category}
   * @return @{@link CategoryFoodItemResponseDto}
   */
  private CategoryFoodItemResponseDto convertCategoryToCategoryFoodItemResponseDto(Category category) {
    if (category == null) {
      return null;
    }
    return new CategoryFoodItemResponseDto(
        category.getCategoryId(),
        category.getName(),
        category.getDescription(),
        category.isActive(),
        category.getCreatedAt(),
        category.getUpdatedAt()
    );
  }

  /**
   * convertFoodImagesToFoodImageFoodItemResponseDtos .
   *
   * @param foodImages @{@link List}
   * @return @{@link List}
   */
  private List<FoodImageFoodItemResponseDto> convertFoodImagesToFoodImageFoodItemResponseDtos(List<FoodImage> foodImages) {
    return foodImages.stream()
        .map(foodImage -> new FoodImageFoodItemResponseDto(
            foodImage.getImageId(),
            foodImage.getImageUrl(),
            foodImage.isActive(),
            foodImage.getCreatedAt(),
            foodImage.getUpdatedAt()
        ))
        .collect(Collectors.toList());
  }

  /**
   * convertRatingsToFoodItemRatingResponseDtos .
   *
   * @param ratings @{@link List}
   * @return @{@link List}
   */
  private List<FoodItemRatingResponseDto> convertRatingsToFoodItemRatingResponseDtos(List<Rating> ratings) {
    return ratings.stream()
        .map(rating -> new FoodItemRatingResponseDto(
            rating.getRatingId(),
            rating.getRatingValue(),
            rating.getReview(),
            rating.isActive(),
            rating.getCreatedAt(),
            rating.getUpdatedAt()
        ))
        .collect(Collectors.toList());
  }
}

