package com.khaofit.khaofitservice.service;

import com.khaofit.khaofitservice.dto.request.AddressDto;
import com.khaofit.khaofitservice.dto.request.EditAddressDto;
import org.springframework.http.ResponseEntity;

/**
 * this is a user address service .
 *
 * @author kousik manik
 */
public interface UserAddressService {

  public ResponseEntity<?> saveUserAddress(AddressDto dto);

  public ResponseEntity<?> updateUserAddress(Long addressId, EditAddressDto dto);

  public ResponseEntity<?> getAllUserAddress(String mobileNumber);

  public ResponseEntity<?> deleteUserAddress(Long addressId);

}
