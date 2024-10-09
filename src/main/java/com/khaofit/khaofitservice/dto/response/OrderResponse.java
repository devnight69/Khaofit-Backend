package com.khaofit.khaofitservice.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khaofit.khaofitservice.deserializer.NotesDeserializer;
import com.khaofit.khaofitservice.deserializer.TimestampDeserializer;
import java.util.List;
import lombok.Data;

/**
 * Order response.
 * This class represents the response received from the Razorpay API for an order.
 * It includes details such as order ID, amount, status, and creation timestamp.
 * The timestamp is converted from epoch milliseconds to a human-readable date-time format.
 *
 *@author  Avinash
 */
@Data
public class OrderResponse {
  private String id;
  private String entity;
  private Integer amount;
  @SuppressWarnings("checkstyle:membername")
  private Integer amount_paid;
  @SuppressWarnings("checkstyle:membername")
  private Integer amount_due;
  private String currency;
  private String receipt;
  @SuppressWarnings("checkstyle:membername")
  private String offer_id;
  private String status;
  private Integer attempts;
  @JsonDeserialize(using = NotesDeserializer.class)
  private List<NotesDto> notes;

  @JsonDeserialize(using = TimestampDeserializer.class)
  @SuppressWarnings("checkstyle:membername")
  private String created_at;

  /**
   * this is notes dto class .
   */
  @Data
  public static class NotesDto {
    @SuppressWarnings("checkstyle:MemberName")
    private String notes_key_1;
    @SuppressWarnings("checkstyle:MemberName")
    private String notes_key_2;
  }
}

