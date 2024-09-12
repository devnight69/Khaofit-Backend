package com.khaofit.khaofitservice.service;

import com.khaofit.khaofitservice.dto.request.CategoryRequestDto;
import org.springframework.http.ResponseEntity;

/**
 * this is a service class for category .
 *
 * @author kousik manik
 */
public interface CategoryService {

  public ResponseEntity<?> createCategory(CategoryRequestDto dto);

  public ResponseEntity<?> getAllCategoryList();

  public ResponseEntity<?> getCategoryDetailsById(Long categoryId);

  public ResponseEntity<?> deActiveCategory(Long categoryId);

}
