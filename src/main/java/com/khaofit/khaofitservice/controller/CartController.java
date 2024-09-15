package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.dto.request.CartRequestDto;
import com.khaofit.khaofitservice.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a controller class for cart .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @PostMapping("/add")
  public ResponseEntity<?> addToCart(@Valid @RequestBody CartRequestDto cartRequestDto) {
    return cartService.addToCart(cartRequestDto);
  }

  @GetMapping("/details")
  public ResponseEntity<?> getAllCartDetails(@Valid @RequestParam("userUlid")
                                             @NotNull(message = "userUlid is required")
                                             @NotEmpty(message = "userUlid is required")
                                             @NotBlank(message = "userUlid is required") String userUlid) {
    return cartService.getAllCartDetails(userUlid);
  }

  @DeleteMapping("/remove")
  public ResponseEntity<?> removeFromCart(@Valid @RequestParam("cartId")
                                          @NotNull(message = "cartId is required") Long cartId) {
    return cartService.removeFromCart(cartId);
  }

}
