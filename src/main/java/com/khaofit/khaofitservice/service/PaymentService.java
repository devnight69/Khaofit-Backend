package com.khaofit.khaofitservice.service;

import com.khaofit.khaofitservice.dto.request.OrderData;
import org.springframework.http.ResponseEntity;

/**
 * this is a payment service class .
 *
 * @author kousik manik
 */
public interface PaymentService {

  public ResponseEntity<?> getPaymentDetails(String orderId);

  public ResponseEntity<?> getRzpKey();

  public ResponseEntity<?> createOrder(String mobileNumber, OrderData orderData);

  public void savePaymentStatus(Object response);

}
