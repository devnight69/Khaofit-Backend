package com.khaofit.khaofitservice.dto.request;

import lombok.Data;

/**
 * Order DTO.
 *
 * @author avinash
 *
 */
@Data
public class OrderData {
  private double amount;
  private String currency = "INR";
  private String receipt;
  private NotesDto notes;

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

