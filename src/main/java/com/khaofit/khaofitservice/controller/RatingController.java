package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.dto.request.RatingRequestDto;
import com.khaofit.khaofitservice.service.RatingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a controller class for rating .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/rating")
public class RatingController {

  @Autowired
  private RatingService ratingService;

  @PostMapping("/submit")
  public ResponseEntity<?> submitRating(@Valid @RequestParam(name = "restaurantId")
                                        @NotNull(message = "restaurantId is required") Long restaurantId,
                                        @Valid @RequestParam(name = "foodItemId")
                                        @NotNull(message = "foodItemId is required") Long foodItemId,
                                        @Valid @RequestBody RatingRequestDto dto) {
    return ratingService.submitRating(restaurantId, foodItemId, dto);
  }

}
