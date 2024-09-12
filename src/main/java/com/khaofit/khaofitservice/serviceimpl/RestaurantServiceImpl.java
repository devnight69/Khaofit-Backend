package com.khaofit.khaofitservice.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaofit.khaofitservice.converter.RestaurantAndFoodItemConverter;
import com.khaofit.khaofitservice.dto.request.RestaurantRequestDto;
import com.khaofit.khaofitservice.dto.response.RestaurantResponseDto;
import com.khaofit.khaofitservice.model.Restaurant;
import com.khaofit.khaofitservice.repository.RestaurantRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.RestaurantService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * this is a restaurant service implementation class .
 *
 * @author kousik manik
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private BaseResponse baseResponse;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private RestaurantAndFoodItemConverter restaurantAndFoodItemConverter;

  private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

  /**
   * this is register restaurant method .
   *
   * @param dto @{@link RestaurantRequestDto}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> registerRestaurant(RestaurantRequestDto dto) {
    try {
      logger.info("Attempting to register a new restaurant with mobile number: {}", dto.getMobileNumber());

      Optional<Restaurant> existingRestaurant = restaurantRepository.findByMobileNumber(dto.getMobileNumber());
      if (existingRestaurant.isPresent()) {
        String errorMessage = "Restaurant is already registered with this mobile number: " + dto.getMobileNumber();
        logger.warn(errorMessage);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, errorMessage);
      }

      Restaurant restaurant = objectMapper.convertValue(dto, Restaurant.class);
      restaurant = restaurantRepository.saveAndFlush(restaurant);

      String successMessage = "Restaurant registered successfully with ID: " + restaurant.getRestaurantId();
      logger.info(successMessage);
      return baseResponse.successResponse(successMessage, restaurant);

    } catch (Exception e) {
      logger.error("Error occurred while registering restaurant: {}", e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred while registering the restaurant.");
    }
  }

  /**
   * this is a method for restaurant deActive .
   *
   * @param restaurantId @{@link Long}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> deActiveRestaurant(Long restaurantId) {
    try {
      int updatedRows = restaurantRepository.deactivateRestaurant(restaurantId);

      if (updatedRows == 0) {
        logger.warn("Restaurant with ID {} not found for deactivation.", restaurantId);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Restaurant not found");
      }

      logger.info("Restaurant with ID {} successfully deactivated.", restaurantId);
      return baseResponse.successResponse("Restaurant deactivated successfully");
    } catch (Exception e) {
      logger.error("Error occurred while deactivating restaurant with ID {}: {}", restaurantId, e.getMessage());
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again.");
    }
  }

  /**
   * this is a get restaurant Details byId .
   *
   * @param restaurantId @{@link Long}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getRestaurantDetailsById(Long restaurantId) {
    logger.info("Fetching details for restaurant with ID: {}", restaurantId);

    try {
      Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);

      if (optionalRestaurant.isEmpty()) {
        logger.warn("Restaurant with ID {} not found", restaurantId);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Restaurant Not Found");
      }

      RestaurantResponseDto restaurantResponseDto = restaurantAndFoodItemConverter.convertToRestaurantResponseDto(
          optionalRestaurant.get());

      logger.info("Successfully retrieved restaurant details for ID: {}", restaurantId);
      return baseResponse.successResponse(restaurantResponseDto);
    } catch (Exception e) {
      logger.error("Error retrieving restaurant details for ID: {} - {}", restaurantId, e.getMessage());
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again.");
    }
  }
}
