package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.constant.KhaoFitConstantService;
import com.khaofit.khaofitservice.dto.request.OrderData;
import com.khaofit.khaofitservice.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a payment controller class .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

  @Autowired
  private PaymentService paymentService;

  @PostMapping("/create")
  public ResponseEntity<?> createOrder(@Valid @RequestParam(name = "mobileNumber")
                                       @NotBlank(message = "Mobile number is required")
                                       @NotEmpty(message = "Mobile number is required")
                                       @NotNull(message = "Mobile number is required")
                                       @Pattern(regexp = KhaoFitConstantService.MOBILE_NUMBER_REGEX,
                                           message = "Invalid mobile number format")
                                       String mobileNumber,
                                       @Valid @RequestBody OrderData orderData) {
    return paymentService.createOrder(mobileNumber, orderData);
  }

  @GetMapping("/status")
  public ResponseEntity<?> getPaymentDetails(@Valid @RequestParam(name = "orderId")
                                             @NotBlank(message = "orderId is required")
                                             @NotEmpty(message = "orderId is required")
                                             @NotNull(message = "orderId is required")
                                             String orderId) {
    return paymentService.getPaymentDetails(orderId);
  }

  @GetMapping("/key")
  public ResponseEntity<?> getRzpKey() {
    return paymentService.getRzpKey();
  }

}
