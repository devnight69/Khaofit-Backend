package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.dto.request.OrderHistoryRequestDto;
import com.khaofit.khaofitservice.service.OrderHistoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a controller class for order history .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/order")
public class OrderHistoryController {

  @Autowired
  private OrderHistoryService orderHistoryService;

  @PostMapping("/submit")
  public ResponseEntity<?> createOrder(@Validated @RequestBody OrderHistoryRequestDto dto) {
    return orderHistoryService.createOrder(dto);
  }

  @GetMapping("/history")
  public ResponseEntity<?> getOrderHistory(@Valid @RequestParam("userUlid")
                                           @NotNull(message = "userUlid is required")
                                           @NotEmpty(message = "userUlid is required")
                                           @NotBlank(message = "userUlid is required")
                                           @Size(min = 26, max = 26, message =
                                               "User ULID must be exactly 26 characters long.") String userUlid) {
    return orderHistoryService.getOrderHistory(userUlid);
  }

}
