package com.khaofit.khaofitservice.dto.response;

import java.util.Map;
import lombok.Data;

/**
 * payment refund response dto for razorpay .
 *
 * @author kousik mnaik
 */
@Data
public class PaymentRefundResponseDto {

  private String id;
  private String entity;
  private long amount;
  private String currency;
  private String paymentId;
  private Map<String, String> notes;
  private String receipt;
  private AcquirerData acquirerData;
  private long createdAt;
  private String batchId;
  private String status;
  private String speedProcessed;
  private String speedRequested;

}

