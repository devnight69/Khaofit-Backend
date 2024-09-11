package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.dto.request.RestaurantRequestDto;
import com.khaofit.khaofitservice.service.RestaurantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a restaurant controller class .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  @PostMapping("/register")
  public ResponseEntity<?> registerRestaurant(@Valid @RequestBody RestaurantRequestDto dto) {
    return restaurantService.registerRestaurant(dto);
  }

  @PutMapping("/deActive")
  public ResponseEntity<?> deActiveRestaurant(@Valid @RequestParam(name = "restaurantId")
                                              @NotNull(message = "restaurantId is required") Long restaurantId) {
    return restaurantService.deActiveRestaurant(restaurantId);
  }

}
