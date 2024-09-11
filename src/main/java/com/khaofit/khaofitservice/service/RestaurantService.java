package com.khaofit.khaofitservice.service;

import com.khaofit.khaofitservice.dto.request.RestaurantRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * this is a restaurant service class .
 *
 * @author kousik mnaik
 */
public interface RestaurantService {

  public ResponseEntity<?> registerRestaurant(RestaurantRequestDto dto);

  public ResponseEntity<?> deActiveRestaurant(Long restaurantId);

}
