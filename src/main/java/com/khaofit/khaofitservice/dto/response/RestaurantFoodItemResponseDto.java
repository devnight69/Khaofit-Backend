package com.khaofit.khaofitservice.dto.response;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this is a restaurant food item response dto .
 *
 * @author kousik manik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantFoodItemResponseDto {

  private Long restaurantId;

  private String ulId;

  private String name;

  private String location;

  private String contactInfo;

  private String mobileNumber;

  private Double rating;

  private String openingHours;

  private boolean active;

  private LocalTime openTime;

  private LocalTime closeTime;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;

}
