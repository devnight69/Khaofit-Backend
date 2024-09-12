package com.khaofit.khaofitservice.dto.response;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this is a food image food item response dto .
 *
 * @author kousik manik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodImageFoodItemResponseDto {

  private Long imageId;

  private String imageUrl;

  private boolean active;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;

}
