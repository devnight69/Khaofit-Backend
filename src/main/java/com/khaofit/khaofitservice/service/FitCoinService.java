package com.khaofit.khaofitservice.service;

import org.springframework.http.ResponseEntity;

/**
 * this is a fit coin service class .
 *
 * @author kousik manik
 */
public interface FitCoinService {

  public ResponseEntity<?> getFitCoinHistory(Long userId);

}
