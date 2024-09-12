package com.khaofit.khaofitservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * this is a food image request dto .
 *
 * @author kousik manik
 */
@Data
public class FoodImageRequestDto {

  @NotNull(message = "Food Image Cannot Be null")
  @NotEmpty(message = "Food Image Cannot Be Empty")
  @NotBlank(message = "Food Image Cannot Be Blank")
  private String imageUrl;

}
