package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.service.FitCoinService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a fit coin details controller .
 *
 * @author kousik mnaik
 */
@RestController
@RequestMapping("/fit/coin")
public class FitCoinDetailsController {

  @Autowired
  private FitCoinService fitCoinService;

  @GetMapping("/user/details")
  public ResponseEntity<?> getFitCoinHistory(@Valid @RequestParam(name = "userId")
                                             @NotNull(message = "userId is required") Long userId) {
    return fitCoinService.getFitCoinHistory(userId);
  }

}
