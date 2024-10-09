package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.constant.KhaoFitConstantService;
import com.khaofit.khaofitservice.service.PaymentService;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a call back controller class for razorpay .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/api/v1/rzp/callback")
public class RazorpayCallbackController {

  @Autowired
  private PaymentService paymentService;

  @PostMapping(KhaoFitConstantService.RZP_CALLBACK)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void savePaymentStatus(@NotNull @RequestBody Object response,
                                @RequestHeader Map<String, String> headers) {
    paymentService.savePaymentStatus(response);
  }

}

