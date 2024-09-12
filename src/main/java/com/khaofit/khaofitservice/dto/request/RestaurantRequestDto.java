package com.khaofit.khaofitservice.dto.request;

import com.khaofit.khaofitservice.constant.KhaoFitConstantService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Request DTO for registering a new Restaurant.
 * Contains validation annotations to ensure proper data is provided.
 *
 * @author kousik manik
 */
@Data
public class RestaurantRequestDto {

  @NotBlank(message = "Restaurant name must not be blank")
  @NotEmpty(message = "Restaurant name must not be empty")
  @NotNull(message = "Restaurant name must not be null")
  @Size(max = 100, message = "Restaurant name must be at most 100 characters")
  private String name; // Name of the restaurant

  @NotBlank(message = "Restaurant location must not be blank")
  @NotEmpty(message = "Restaurant location must not be empty")
  @NotNull(message = "Restaurant location must not be null")
  private String location; // Location of the restaurant

  @NotBlank(message = "Contact info must not be blank")
  @NotEmpty(message = "Contact info must not be empty")
  @NotNull(message = "Contact info must not be null")
  private String contactInfo; // Contact information of the restaurant

  @NotBlank(message = "Mobile number must not be blank")
  @NotEmpty(message = "Mobile number must not be empty")
  @NotNull(message = "Mobile number must not be null")
  @Pattern(regexp = KhaoFitConstantService.MOBILE_NUMBER_REGEX, message = "Incorrect phone number.")
  private String mobileNumber; // Mobile number of the restaurant

  @NotBlank(message = "Opening hours must not be blank")
  @NotEmpty(message = "Opening hours must not be empty")
  @NotNull(message = "Opening hours must not be null")
  private String openingHours; // Opening hours of the restaurant

  @NotNull(message = "Open time must not be null")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalTime openTime; // Opening time of the restaurant

  @NotNull(message = "Close time must not be null")
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalTime closeTime; // C

}

