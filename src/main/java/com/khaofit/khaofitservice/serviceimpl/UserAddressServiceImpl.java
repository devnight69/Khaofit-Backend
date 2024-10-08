package com.khaofit.khaofitservice.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaofit.khaofitservice.dto.request.AddressDto;
import com.khaofit.khaofitservice.dto.request.EditAddressDto;
import com.khaofit.khaofitservice.model.Address;
import com.khaofit.khaofitservice.repository.AddressRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.UserAddressService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * this is a service implementation class for user address .
 *
 * @author kousik manik
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {


  @Autowired
  private AddressRepository addressRepo;

  @Autowired
  private BaseResponse baseResponse;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EntityManager entityManager;

  private static final Logger logger = LoggerFactory.getLogger(UserAddressServiceImpl.class);


  private static Map<String, Object> getStringObjectMap(EditAddressDto dto) {
    Map<String, Object> fieldsToUpdate = new HashMap<>();
    fieldsToUpdate.put("fullName", dto.getFullName());
    fieldsToUpdate.put("mobileNumber", dto.getMobileNumber());
    fieldsToUpdate.put("alternateMobileNumber", dto.getAlternateMobileNumber());
    fieldsToUpdate.put("emailId", dto.getEmailId());
    fieldsToUpdate.put("age", dto.getAge());
    fieldsToUpdate.put("gender", dto.getGender());
    fieldsToUpdate.put("houseFlatNumber", dto.getHouseFlatNumber());
    fieldsToUpdate.put("apartmentArea", dto.getApartmentArea());
    fieldsToUpdate.put("landmark", dto.getLandmark());
    fieldsToUpdate.put("pinCode", dto.getPinCode());
    fieldsToUpdate.put("addressType", dto.getAddressType());
    fieldsToUpdate.put("stateName", dto.getStateName());
    fieldsToUpdate.put("districtName", dto.getDistrictName());
    return fieldsToUpdate;
  }

  /**
   * Adds a new address to the repository.
   *
   * @param dto the AddressDto containing the address details
   * @return ResponseEntity containing the saved Address object on success or an error response on failure
   */
  @Override
  public ResponseEntity<?> saveUserAddress(AddressDto dto) {
    try {
      logger.info("Attempting to convert AddressDto to Address entity");
      Address address = objectMapper.convertValue(dto, Address.class);

      logger.info("Saving the Address entity to the repository");
      Address savedAddress = addressRepo.saveAndFlush(address);

      logger.info("Address entity saved successfully with ID: {}", savedAddress.getId());
      return baseResponse.successResponse(savedAddress);
    } catch (Exception e) {
      logger.error("Error occurred while adding the address: {}", e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add address");
    }
  }

  /**
   * Edits an address with the given details. Only fields present in the dto will be updated.
   *
   * @param addressId the ID of the address to be updated
   * @param dto the EditAddressDto containing the address details to be updated
   * @return ResponseEntity indicating the result of the edit operation
   */
  @Override
  @Transactional
  public ResponseEntity<?> updateUserAddress(Long addressId, EditAddressDto dto) {
    try {
      logger.info("Attempting to edit address with ID: {}", addressId);

      Map<String, Object> fieldsToUpdate = getStringObjectMap(dto);

      // Remove null values
      fieldsToUpdate.values().removeIf(Objects::isNull);

      if (fieldsToUpdate.isEmpty()) {
        logger.warn("No fields provided for update for address ID: {}", addressId);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "No fields provided for update");
      }

      StringBuilder jpql = new StringBuilder("UPDATE Address a SET ");
      for (String fieldName : fieldsToUpdate.keySet()) {
        jpql.append("a.").append(fieldName).append(" = :").append(fieldName).append(", ");
      }
      jpql.delete(jpql.length() - 2, jpql.length()); // Remove the last comma and space
      jpql.append(" WHERE a.id = :id");

      Query query = entityManager.createQuery(jpql.toString());
      fieldsToUpdate.forEach(query::setParameter);
      query.setParameter("id", addressId);

      int updatedEntities = query.executeUpdate();

      if (updatedEntities == 1) {
        logger.info("Successfully updated address with ID: {}", addressId);
        return baseResponse.successResponse("Address updated successfully");
      } else {
        logger.warn("Address not found for ID: {}", addressId);
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Address does not exist");
      }
    } catch (Exception e) {
      logger.error("An error occurred while updating address with ID: {}: {}", addressId, e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update address");
    }
  }

  /**
   * Retrieves all addresses associated with the given mobile number.
   *
   * @param mobileNumber the mobile number associated with the addresses
   * @return ResponseEntity containing the list of addresses sorted by creation date
   *         in descending order, or an error response
   */
  @Override
  public ResponseEntity<?> getAllUserAddress(String mobileNumber) {
    try {
      logger.info("Retrieving all addresses for mobile number: {}", mobileNumber);

      List<Address> addressList = addressRepo.findByUserMobileNumber(mobileNumber);

      logger.info("Found {} addresses for mobile number: {}", addressList.size(), mobileNumber);

      List<Address> sortedAddressList = addressList.stream()
          .sorted(Comparator.comparing((Address address) ->
                  address != null ? address.getCreatedAt() : null,
              Comparator.nullsLast(Comparator.reverseOrder())))
          .collect(Collectors.toList());

      logger.info("Addresses sorted by creation date in descending order for mobile number: {}", mobileNumber);

      return baseResponse.successResponse(sortedAddressList);
    } catch (Exception e) {
      logger.error("Error occurred while retrieving addresses for mobile number: {}", mobileNumber, e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve addresses");
    }
  }

  /**
   * Deletes an address from the repository by its ID.
   *
   * @param addressId the ID of the address to be deleted
   * @return ResponseEntity indicating the result of the delete operation
   */
  @Override
  public ResponseEntity<?> deleteUserAddress(Long addressId) {
    try {
      logger.info("Attempting to delete address with ID: {}", addressId);

      if (addressRepo.existsById(addressId)) {
        addressRepo.deleteById(addressId);
        logger.info("Address with ID: {} deleted successfully", addressId);
        return baseResponse.successResponse("Address deleted successfully");
      } else {
        logger.warn("Address with ID: {} not found", addressId);
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Address not found");
      }
    } catch (Exception e) {
      logger.error("Error occurred while deleting address with ID: {}", addressId, e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete address");
    }
  }
}
