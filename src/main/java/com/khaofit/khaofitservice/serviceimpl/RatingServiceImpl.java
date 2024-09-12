package com.khaofit.khaofitservice.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaofit.khaofitservice.dto.request.RatingRequestDto;
import com.khaofit.khaofitservice.model.FoodItem;
import com.khaofit.khaofitservice.model.Rating;
import com.khaofit.khaofitservice.model.Restaurant;
import com.khaofit.khaofitservice.repository.FoodItemRepository;
import com.khaofit.khaofitservice.repository.RatingRepository;
import com.khaofit.khaofitservice.repository.RestaurantRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.RatingService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * this is a rating service implementation class .
 *
 * @author kousik manik
 */
@Service
public class RatingServiceImpl implements RatingService {

  @Autowired
  private RatingRepository ratingRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private FoodItemRepository foodItemRepository;

  @Autowired
  private BaseResponse baseResponse;

  @Autowired
  private ObjectMapper objectMapper;

  private static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

  /**
   * this is a submit rating method .
   *
   * @param restaurantId @{@link Long}
   * @param foodItemId @{@link Long}
   * @param dto @{@link RatingRequestDto}
   * @return @{@link ResponseEntity}
   */
  @Override
  @Transactional
  public ResponseEntity<?> submitRating(Long restaurantId, Long foodItemId, RatingRequestDto dto) {
    try {
      logger.debug("Received request to submit rating for foodItemId: {} at restaurantId: {}", foodItemId, restaurantId);

      Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
      if (optionalRestaurant.isEmpty()) {
        String errorMessage = "Restaurant with ID " + restaurantId + " not found";
        logger.warn(errorMessage);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, errorMessage);
      }
      Restaurant restaurant = optionalRestaurant.get();

      Optional<FoodItem> optionalFoodItem = foodItemRepository.findById(foodItemId);
      if (optionalFoodItem.isEmpty()) {
        String errorMessage = "Food Item with ID " + foodItemId + " not found";
        logger.warn(errorMessage);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, errorMessage);
      }
      FoodItem foodItem = optionalFoodItem.get();

      Rating rating = objectMapper.convertValue(dto, Rating.class);
      rating.setRestaurant(restaurant);
      rating.setFoodItem(foodItem);

      ratingRepository.saveAndFlush(rating);

      logger.info("Successfully saved rating for foodItemId: {} at restaurantId: {}", foodItemId, restaurantId);
      return baseResponse.successResponse("Thank you for your rating!");

    } catch (Exception e) {
      logger.error("Failed to submit rating for foodItemId: {} at restaurantId: {}. Error: {}", foodItemId,
          restaurantId, e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to submit rating. Please try again later.");
    }
  }

}
