package com.khaofit.khaofitservice.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaofit.khaofitservice.converter.FoodItemToFoodItemResponseDtoConverter;
import com.khaofit.khaofitservice.dto.request.FoodItemRegisterDto;
import com.khaofit.khaofitservice.dto.response.FoodItemResponseDto;
import com.khaofit.khaofitservice.model.Category;
import com.khaofit.khaofitservice.model.FoodImage;
import com.khaofit.khaofitservice.model.FoodItem;
import com.khaofit.khaofitservice.model.Restaurant;
import com.khaofit.khaofitservice.repository.CategoryRepository;
import com.khaofit.khaofitservice.repository.FoodImageRepository;
import com.khaofit.khaofitservice.repository.FoodItemRepository;
import com.khaofit.khaofitservice.repository.RestaurantRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.FoodItemService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * this is a food item service implementation class for food service .
 *
 * @author kousik manik
 */
@Service
public class FoodItemServiceImpl implements FoodItemService {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private FoodImageRepository foodImageRepository;

  @Autowired
  private FoodItemRepository foodItemRepository;

  @Autowired
  private BaseResponse baseResponse;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private FoodItemToFoodItemResponseDtoConverter foodItemToFoodItemResponseDtoConverter;

  private static final Logger logger = LoggerFactory.getLogger(FoodItemServiceImpl.class);

  /**
   * this is a register food item method .
   *
   * @param restaurantId @{@link Long}
   * @param categoryId @{@link Long}
   * @param dto @{@link FoodItemRegisterDto}
   * @return @{@link ResponseEntity}
   */
  @Override
  @Transactional
  public ResponseEntity<?> registerFoodItem(Long restaurantId, Long categoryId, FoodItemRegisterDto dto) {
    logger.info("Attempting to register food item: {} for restaurant ID: {} and category ID: {}", dto.getName(),
        restaurantId, categoryId);

    try {
      // Fetch Restaurant
      Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
      if (optionalRestaurant.isEmpty()) {
        logger.warn("Restaurant not found with ID: {}", restaurantId);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Restaurant Not Found");
      }

      // Fetch Category
      Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
      if (optionalCategory.isEmpty()) {
        logger.warn("Category not found with ID: {}", categoryId);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Category Not Found");
      }

      // Check if Food Item Already Exists
      boolean foodItemExists = foodItemRepository.existsByRestaurantAndCategoryAndName(
          optionalRestaurant.get(), optionalCategory.get(), dto.getName());

      if (foodItemExists) {
        logger.warn("Food item '{}' already registered under restaurant ID: {} and category ID: {}", dto.getName(),
            restaurantId, categoryId);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "This Food Is Already Registered With Us");
      }

      // Convert DTO to FoodItem Entity
      FoodItem foodItem = objectMapper.convertValue(dto, FoodItem.class);
      foodItem.setRestaurant(optionalRestaurant.get());
      foodItem.setCategory(optionalCategory.get());

      // Save FoodItem Entity
      foodItem = foodItemRepository.saveAndFlush(foodItem);
      logger.info("Successfully saved food item: {} with ID: {}", foodItem.getName(), foodItem.getFoodId());

      // Process Food Images
      FoodItem finalFoodItem = foodItem;
      List<FoodImage> foodImageList = dto.getFoodImageRequestDto().stream().map(foodImageRequestDto -> {
        FoodImage foodImage = new FoodImage();
        foodImage.setImageUrl(foodImageRequestDto.getImageUrl());
        foodImage.setFoodItem(finalFoodItem);
        return foodImage;
      }).collect(Collectors.toList());

      // Save Food Images
      foodImageRepository.saveAllAndFlush(foodImageList);
      logger.info("Saved {} images for food item ID: {}", foodImageList.size(), foodItem.getFoodId());

      return baseResponse.successResponse("Food Registered Successfully");

    } catch (Exception e) {
      logger.error("Error occurred while registering food item: {}", e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again later.");
    }
  }

  /**
   * this is a get food item details method .
   *
   * @param foodItemId @{@link Long}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getFoodItemDetails(Long foodItemId) {
    try {
      // Log the start of the method
      logger.info("Fetching details for Food Item with ID: {}", foodItemId);

      // Find the food item by ID
      Optional<FoodItem> optionalFoodItem = foodItemRepository.findByIdWithDetails(foodItemId);

      // Check if the food item is present
      if (optionalFoodItem.isEmpty()) {
        logger.warn("Food Item with ID: {} not found", foodItemId);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Food Item Not Found");
      }

      // Convert FoodItem to FoodItemResponseDto
      FoodItemResponseDto foodItemResponseDto = foodItemToFoodItemResponseDtoConverter.convert(optionalFoodItem.get());
      logger.info("Successfully converted Food Item with ID: {} to FoodItemResponseDto", foodItemId);

      // Return success response
      return baseResponse.successResponse(foodItemResponseDto);
    } catch (Exception e) {
      // Log the exception details
      logger.error("An error occurred while fetching Food Item details for ID: {}: {}", foodItemId, e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again later.");
    }
  }


}
