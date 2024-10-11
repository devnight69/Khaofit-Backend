package com.khaofit.khaofitservice.dto.error;

import lombok.Data;

/**
 * Data Transfer Object for representing an error response from RazorPay.
 * This class encapsulates information about an error, including error code,
 * description, source, step, reason, metadata, and field.
 *
 * @author kousik
 */
@Data
public class RzpErrorResponseClass {

  private ErrorDto error;

  /**
   * Data Transfer Object for representing details of an error.
   * This inner class contains attributes such as error code, description,
   * source, step, reason, metadata, and field associated with an error.
   */
  @Data
  public static class ErrorDto {
    private String code;
    private String description;
    private String source;
    private String step;
    private String reason;
    private Object metadata;
    private String field;
  }

}


