package com.khaofit.khaofitservice.dto.response;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this is a food item rating response dto .
 *
 * @author kousik manik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemRatingResponseDto {

  private Long ratingId;

  private Integer ratingValue;

  private String review;

  private boolean active;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;

}
