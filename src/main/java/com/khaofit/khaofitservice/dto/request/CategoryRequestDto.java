package com.khaofit.khaofitservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request DTO for creating or updating a Category.
 * Contains validation annotations to ensure proper data is provided.
 *
 * @author kousik manik
 */
@Data
public class CategoryRequestDto {

  @NotBlank(message = "Category name must not be blank")
  @NotEmpty(message = "Category name must not be empty")
  @NotNull(message = "Category name must not be null")
  @Size(max = 100, message = "Category name must be at most 100 characters")
  private String name; // Name of the category

  @NotBlank(message = "Category description must not be blank")
  @NotEmpty(message = "Category description must not be empty")
  @NotNull(message = "Category description must not be null")
  private String description; // Description of the category

}

