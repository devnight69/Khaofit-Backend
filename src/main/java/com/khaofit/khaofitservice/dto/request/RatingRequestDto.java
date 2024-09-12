package com.khaofit.khaofitservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for submitting a rating.
 *
 * @author kousik manik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestDto {

  @NotNull(message = "Rating value cannot be null")
  @Min(value = 1, message = "Rating value must be at least 1")
  @Max(value = 5, message = "Rating value must not exceed 5")
  private Integer ratingValue;

  @NotBlank(message = "Review cannot be blank")
  @NotNull(message = "Review cannot be null")
  @NotEmpty(message = "Review cannot be empty")
  private String review;

  @NotNull(message = "username value cannot be null")
  @NotBlank(message = "username value cannot be blank")
  @NotEmpty(message = "username value cannot be empty")
  private String username;

}

