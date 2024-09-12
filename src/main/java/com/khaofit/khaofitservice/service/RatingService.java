package com.khaofit.khaofitservice.service;

import com.khaofit.khaofitservice.dto.request.RatingRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * this is a rating service class .
 *
 * @author kousik manik
 */
public interface RatingService {

  public ResponseEntity<?> submitRating(Long restaurantId, Long foodItemId, RatingRequestDto dto);

}
