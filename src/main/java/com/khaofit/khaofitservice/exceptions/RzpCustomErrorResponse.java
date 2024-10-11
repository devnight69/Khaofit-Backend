package com.khaofit.khaofitservice.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaofit.khaofitservice.dto.error.RzpErrorResponseClass;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.utilities.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * Component responsible for customizing error responses from the RazorPay API.
 * This class provides methods to handle and customize error responses received
 * from the RazorPay API, ensuring a consistent and user-friendly error experience.
 *
 * @author kousik
 */
@Component
public class RzpCustomErrorResponse {

  private BaseResponse baseResponse = new BaseResponse();

  /**
   * Setting error response.
   *
   * @param exception {@link HttpStatusCodeException}
   * @return ResponseEntity
   */
  public ResponseEntity<?> setErrorResponse(HttpStatusCodeException exception) throws KhaoFitException {
    try {
      return setCustomMessageErrorResponse(exception);
    } catch (Exception e) {
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  private ResponseEntity<?> setCustomMessageErrorResponse(HttpStatusCodeException exception) throws KhaoFitException,
      JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    RzpErrorResponseClass exData = new RzpErrorResponseClass();
    String exBody = exception.getResponseBodyAsString();

    if (StringUtils.isNotNullAndNotEmpty(exBody)) {
      exData = objectMapper.readValue(exBody, RzpErrorResponseClass.class);
    }
    return baseResponse.errorResponse((HttpStatus) exception.getStatusCode(), exData);
  }

}

