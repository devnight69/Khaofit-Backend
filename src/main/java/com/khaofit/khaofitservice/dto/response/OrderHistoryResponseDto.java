package com.khaofit.khaofitservice.dto.response;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;

/**
 * DTO for OrderHistory response.
 * It represents the data returned when retrieving order history details.
 *
 * @author Kousik Manik
 */
@Data
public class OrderHistoryResponseDto {

  private Long orderHistoryId;
  private List<FoodItemResponseDto> foodDetails;
  private String userUlid;
  private Integer quantity;
  private Double price;
  private Boolean active;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}

