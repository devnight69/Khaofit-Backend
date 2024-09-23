package com.khaofit.khaofitservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * this is a cart request dto .
 *
 * @author kousik manik
 */
@Data
public class CartRequestDto {

  @NotNull(message = "Food item ID is required")
  @Positive(message = "Food item ID must be a positive number")
  private Long foodItemId;

  @NotBlank(message = "User ULID is required")
  @NotEmpty(message = "User ULID is required")
  @NotNull(message = "User ULID is required")
  private String userUlid;

}
