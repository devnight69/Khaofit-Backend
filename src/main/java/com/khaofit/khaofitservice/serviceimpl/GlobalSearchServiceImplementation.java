package com.khaofit.khaofitservice.serviceimpl;

import com.khaofit.khaofitservice.dto.response.SearchResultDto;
import com.khaofit.khaofitservice.model.FoodItem;
import com.khaofit.khaofitservice.model.Restaurant;
import com.khaofit.khaofitservice.repository.FoodItemRepository;
import com.khaofit.khaofitservice.repository.RestaurantRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.GlobalSearchService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * this is a global search service implementation .
 *
 * @author kousik manik
 */
@Service
public class GlobalSearchServiceImplementation implements GlobalSearchService {

  @Autowired
  private BaseResponse baseResponse;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private FoodItemRepository foodItemRepository;

  private static final Logger logger = LoggerFactory.getLogger(GlobalSearchServiceImplementation.class);


  /**
   * this is a global search for restaurant and food item and location .
   *
   * @param input @{@link String}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> searchByInput(String input) {
    logger.info("Starting search for input: {}", input);  // logger the beginning of the search process

    try {
      Pageable pageable = PageRequest.of(0, 20); // Define pagination with 20 results per page
      logger.debug("Pagination set to page size: {}", pageable.getPageSize());

      // Search for restaurants by name or location with pagination
      List<Restaurant> restaurants = restaurantRepository.searchByNameOrLocation(input, pageable);
      logger.info("Found {} restaurants matching input: {}", restaurants.size(), input);

      List<SearchResultDto> results = new ArrayList<>(restaurants.stream().map(this::mapToSearchResultDto).toList());

      // Search for food items by name with pagination
      List<FoodItem> foodItems = foodItemRepository.searchByName(input, pageable);
      logger.info("Found {} food items matching input: {}", foodItems.size(), input);

      results.addAll(foodItems.stream().map(this::mapToSearchResultDto).toList());

      logger.info("Total search results count: {}", results.size());
      return baseResponse.successResponse(results);
    } catch (Exception e) {
      logger.error("Error occurred while searching for input: {}: {}", input, e.getMessage(), e);
      // logger the error details
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "Failed to perform search. Please try again later.");
    }
  }


  /**
   * this is a map to search result dto .
   *
   * @param restaurant @{@link Restaurant}
   * @return @{@link SearchResultDto}
   */
  private SearchResultDto mapToSearchResultDto(Restaurant restaurant) {
    return new SearchResultDto(restaurant.getName(), restaurant.getLocation(), "Restaurant");
  }

  /**
   * this is a map to search result dto .
   *
   * @param foodItem @{@link FoodItem}
   * @return @{@link SearchResultDto}
   */
  private SearchResultDto mapToSearchResultDto(FoodItem foodItem) {
    return new SearchResultDto(foodItem.getName(), foodItem.getCategory().getName(), "Food Item");
  }
}
