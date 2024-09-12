package com.khaofit.khaofitservice.converter;


import com.khaofit.khaofitservice.dto.response.CategoryFoodItemResponseDto;
import com.khaofit.khaofitservice.dto.response.FoodImageFoodItemResponseDto;
import com.khaofit.khaofitservice.dto.response.FoodItemRatingResponseDto;
import com.khaofit.khaofitservice.dto.response.FoodItemResponseDto;
import com.khaofit.khaofitservice.dto.response.RestaurantFoodItemResponseDto;
import com.khaofit.khaofitservice.model.FoodItem;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Custom converter class for converting FoodItem to FoodItemResponseDto.
 *
 * @author kousik manik
 */
@Component
public class FoodItemToFoodItemResponseDtoConverter implements Converter<FoodItem, FoodItemResponseDto> {

  private static final Logger log = LoggerFactory.getLogger(FoodItemToFoodItemResponseDtoConverter.class);

  @Override
  public @NotNull FoodItemResponseDto convert(@NotNull FoodItem source) {
    log.debug("Converting FoodItem with ID: {}", source.getFoodId());

    return new FoodItemResponseDto(
        source.getFoodId(),
        source.getName(),
        source.getDescription(),
        source.getPrice(),
        source.isActive(),
        source.getCreatedAt(),
        source.getUpdatedAt(),
        getRestaurantFoodItemResponseDto(source),
        getCategoryFoodItemResponseDto(source),
        getFoodImageDetails(source),
        getRatingDetails(source)
    );
  }

  private @NotNull List<FoodImageFoodItemResponseDto> getFoodImageDetails(FoodItem source) {
    if (source.getImages() == null || source.getImages().isEmpty()) {
      log.debug("No images found for FoodItem ID: {}", source.getFoodId());
      return List.of();
    }

    return source.getImages().parallelStream()
        .map(foodImage -> new FoodImageFoodItemResponseDto(
            foodImage.getImageId(),
            foodImage.getImageUrl(),
            foodImage.isActive(),
            foodImage.getCreatedAt(),
            foodImage.getUpdatedAt()
        ))
        .collect(Collectors.toList());
  }

  private @NotNull List<FoodItemRatingResponseDto> getRatingDetails(FoodItem source) {
    if (source.getRatings() == null || source.getRatings().isEmpty()) {
      log.debug("No ratings found for FoodItem ID: {}", source.getFoodId());
      return List.of();
    }

    return source.getRatings().parallelStream()
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

  private static @NotNull CategoryFoodItemResponseDto getCategoryFoodItemResponseDto(FoodItem source) {
    return new CategoryFoodItemResponseDto(
        source.getCategory().getCategoryId(),
        source.getCategory().getName(),
        source.getCategory().getDescription(),
        source.getCategory().isActive(),
        source.getCategory().getCreatedAt(),
        source.getCategory().getUpdatedAt()
    );
  }

  private static @NotNull RestaurantFoodItemResponseDto getRestaurantFoodItemResponseDto(FoodItem source) {
    return new RestaurantFoodItemResponseDto(
        source.getRestaurant().getRestaurantId(),
        source.getRestaurant().getUlId(),
        source.getRestaurant().getName(),
        source.getRestaurant().getLocation(),
        source.getRestaurant().getContactInfo(),
        source.getRestaurant().getMobileNumber(),
        source.getRestaurant().getRating(),
        source.getRestaurant().getOpeningHours(),
        source.getRestaurant().isActive(),
        source.getRestaurant().getOpenTime(),
        source.getRestaurant().getCloseTime(),
        source.getRestaurant().getCreatedAt(),
        source.getRestaurant().getUpdatedAt()
    );
  }
}
