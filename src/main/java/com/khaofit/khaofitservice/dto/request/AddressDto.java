package com.khaofit.khaofitservice.dto.request;

import com.khaofit.khaofitservice.castomvalidator.EmailOrBlank;
import com.khaofit.khaofitservice.castomvalidator.MobileNumberOrBlank;
import com.khaofit.khaofitservice.constant.KhaoFitConstantService;
import com.khaofit.khaofitservice.enums.UserGender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * this is a request dto class for address .
 *
 * @author kousik manik
 */
@Data
public class AddressDto {

  @NotEmpty(message = "fullName cannot be empty")
  @NotBlank(message = "fullName cannot be blank")
  @NotNull(message = "fullName cannot be null")
  private String fullName;

  @NotNull(message = "mobileNumber cannot be null")
  @NotEmpty(message = "mobileNumber cannot be empty")
  @Pattern(regexp = KhaoFitConstantService.MOBILE_NUMBER_REGEX, message = "Incorrect user mobile number.")
  private String userMobileNumber;

  @NotNull(message = "mobileNumber cannot be null")
  @NotEmpty(message = "mobileNumber cannot be empty")
  @Pattern(regexp = KhaoFitConstantService.MOBILE_NUMBER_REGEX, message = "Incorrect mobile number.")
  private String mobileNumber;

  @EmailOrBlank
  private String emailId;

  private UserGender gender;

  @MobileNumberOrBlank
  private String alternateMobileNumber;

  @NotEmpty(message = "houseFlatNumber cannot be empty")
  @NotBlank(message = "houseFlatNumber cannot be blank")
  @NotNull(message = "houseFlatNumber cannot be null")
  private String houseFlatNumber;

  private String apartmentArea;

  @NotEmpty(message = "landmark cannot be empty")
  @NotBlank(message = "landmark cannot be blank")
  @NotNull(message = "landmark cannot be null")
  private String landmark;

  @NotEmpty(message = "pinCode cannot be empty")
  @NotNull(message = "pinCode cannot be null")
  @Pattern(regexp = "^[0-9]{6}$", message = "Pin code must be a 6 digit number")
  private String pinCode;

  private String addressType;

  @NotEmpty(message = "stateName cannot be empty")
  @NotBlank(message = "stateName cannot be blank")
  @NotNull(message = "stateName cannot be null")
  private String stateName;

  @NotEmpty(message = "districtName cannot be empty")
  @NotBlank(message = "districtName cannot be blank")
  @NotNull(message = "districtName cannot be null")
  private String districtName;

}
