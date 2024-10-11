package com.khaofit.khaofitservice.dto.request;

import java.util.Map;
import lombok.Data;

/**
 * this is a payment refund request dto for razorpay .
 *
 * @author kousik manik
 */
@Data
public class PaymentRefundRequestDto {

  private Long amount;
  private String receipt;
  private Map<String, String> notes;

}

