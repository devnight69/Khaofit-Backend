package com.khaofit.khaofitservice.service;

import com.khaofit.khaofitservice.dto.request.ChangeOrderStatusRequestDto;
import com.khaofit.khaofitservice.dto.request.OrderHistoryRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * this is a order history service class .
 *
 * @author kousik manik
 */
public interface OrderHistoryService {

  public ResponseEntity<?> createOrder(OrderHistoryRequestDto dto);

  public ResponseEntity<?> getOrderHistory(String userUlid);

  public ResponseEntity<?> changeOrderStatus(ChangeOrderStatusRequestDto dto);

  public ResponseEntity<?> cancelOrder(Long orderId);

}
