package com.khaofit.khaofitservice.dto.response;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this is a category food item response dto .
 *
 * @author kousik manik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFoodItemResponseDto {

  private Long categoryId;

  private String name;

  private String description;

  private boolean active;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;

}
