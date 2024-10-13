package com.khaofit.khaofitservice.dto.jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * JWT Data.
 */
@Setter
@Getter
public class JwtPayloadDto {
  private String fullName;
  private String mobileNumber;
  private String userId;
  private String userStatus;
}

