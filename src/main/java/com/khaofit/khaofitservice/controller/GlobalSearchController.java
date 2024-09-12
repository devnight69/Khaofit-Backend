package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.service.GlobalSearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a global search controller .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/v1/search")
public class GlobalSearchController {

  @Autowired
  private GlobalSearchService globalSearchService;

  @GetMapping("/input")
  public ResponseEntity<?> searchByInput(@Valid @RequestParam(name = "input")
                                         @NotNull(message = "input cannot be null")
                                         @NotEmpty(message = "input cannot be empty") String input) {
    return globalSearchService.searchByInput(input);
  }

}
