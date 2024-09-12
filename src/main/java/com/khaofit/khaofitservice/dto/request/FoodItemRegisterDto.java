package com.khaofit.khaofitservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

/**
 * This is a food item register DTO.
 * Contains validation annotations to ensure proper data is provided.
 *
 * @author kousik manik
 */
@Data
public class FoodItemRegisterDto {

  @NotBlank(message = "Food item name must not be blank")
  @Size(max = 100, message = "Food item name must be at most 100 characters")
  private String name; // Name of the food item

  @NotBlank(message = "Food item description must not be blank")
  @Size(max = 500, message = "Food item description must be at most 500 characters")
  private String description; // Description of the food item

  @NotNull(message = "Price must not be null")
  @Positive(message = "Price must be a positive number")
  private Double price; // Price of the food item

  @Valid
  @NotNull(message = "Food image details must not be null")
  private List<FoodImageRequestDto> foodImageRequestDto; // Image details for the food item
}
