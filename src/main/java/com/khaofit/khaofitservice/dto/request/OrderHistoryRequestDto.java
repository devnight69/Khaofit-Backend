package com.khaofit.khaofitservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

/**
 * DTO for OrderHistory request with validation.
 * It represents the data needed to create or update an order history entry.
 *
 * @author kousik manik
 */
@Data
public class OrderHistoryRequestDto {

  @NotEmpty(message = "Cart IDs cannot be empty")
  private List<@NotNull(message = "Cart ID cannot be null") @Min(value = 1,
      message = "Cart ID must be a positive number") Long> cartId;

  @NotBlank(message = "User ULID cannot be blank")
  @Size(min = 26, max = 26, message = "User ULID must be exactly 26 characters long.")
  private String userUlid;

  @NotNull(message = "Quantity cannot be null")
  @Min(value = 1, message = "Quantity must be at least 1")
  private Integer quantity;

  @NotNull(message = "Price cannot be null")
  @Positive(message = "Price must be a positive value")
  private Double price;

}
