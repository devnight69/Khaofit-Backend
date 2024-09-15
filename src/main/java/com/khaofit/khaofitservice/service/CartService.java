package com.khaofit.khaofitservice.service;

import com.khaofit.khaofitservice.dto.request.CartRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * this is a service class for cart .
 *
 * @author kousik manik
 */
public interface CartService {

  public ResponseEntity<?> addToCart(CartRequestDto cartRequestDto);

  public ResponseEntity<?> getAllCartDetails(String userUlid);

  public ResponseEntity<?> removeFromCart(Long cartId);

}
