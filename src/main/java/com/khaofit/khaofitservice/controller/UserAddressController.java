package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.constant.KhaoFitConstantService;
import com.khaofit.khaofitservice.dto.request.AddressDto;
import com.khaofit.khaofitservice.dto.request.EditAddressDto;
import com.khaofit.khaofitservice.service.UserAddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a controller class for address .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/api/v1/address")
public class UserAddressController {

  @Autowired
  private UserAddressService userAddressService;


  @PostMapping("/add")
  public ResponseEntity<?> addAddress(@Valid @RequestBody AddressDto dto) {
    return userAddressService.saveUserAddress(dto);
  }

  @PutMapping("/edit")
  public ResponseEntity<?> editAddress(@Valid @RequestParam @NotNull(message = "addressId cannot be empty")
                                       Long addressId, @RequestBody EditAddressDto dto) {
    return userAddressService.updateUserAddress(addressId, dto);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<?> deleteAddress(@Valid @RequestParam
                                         @NotNull(message = "addressId cannot be empty") Long addressId) {
    return userAddressService.deleteUserAddress(addressId);
  }

  @GetMapping("/get/all")
  public ResponseEntity<?> getAllAddress(@Valid @RequestParam(name = "mobileNumber")
                                         @NotBlank(message = "Mobile number is required")
                                         @Pattern(regexp = KhaoFitConstantService.MOBILE_NUMBER_REGEX,
                                             message = "Invalid mobile number format")
                                         String mobileNumber) {
    return userAddressService.getAllUserAddress(mobileNumber);
  }


}
