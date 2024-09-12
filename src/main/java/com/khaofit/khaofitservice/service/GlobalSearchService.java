package com.khaofit.khaofitservice.service;

import org.springframework.http.ResponseEntity;

/**
 * this is a global search service class .
 *
 * @author kousik manik
 */
public interface GlobalSearchService {

  public ResponseEntity<?> searchByInput(String input);

}
