package com.khaofit.khaofitservice.dto.request;

import com.khaofit.khaofitservice.castomvalidator.MobileNumberOrBlank;
import com.khaofit.khaofitservice.enums.UserGender;
import lombok.Data;

/**
 * this is a edit address request dto .
 *
 * @author kousik
 */
@Data
public class EditAddressDto {

  private String fullName;

  @MobileNumberOrBlank
  private String mobileNumber;

  @MobileNumberOrBlank
  private String alternateMobileNumber;

  private String emailId;

  private Integer age;

  private UserGender gender;

  private String houseFlatNumber;

  private String apartmentArea;

  private String landmark;

  private String pinCode;

  private String addressType;

  private String stateName;

  private String districtName;


}
