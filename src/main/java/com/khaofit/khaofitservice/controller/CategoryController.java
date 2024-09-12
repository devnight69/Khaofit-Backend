package com.khaofit.khaofitservice.controller;

import com.khaofit.khaofitservice.dto.request.CategoryRequestDto;
import com.khaofit.khaofitservice.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a controller class for category .
 *
 * @author kousik manik
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @PostMapping("/create")
  public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto dto) {
    return categoryService.createCategory(dto);
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllCategoryList() {
    return categoryService.getAllCategoryList();
  }

  @GetMapping("/detailsById")
  public ResponseEntity<?> getCategoryDetailsById(@Valid @RequestParam(name = "categoryId")
                                                  @NotNull(message = "categoryId is required") Long categoryId) {
    return categoryService.getCategoryDetailsById(categoryId);
  }

  @PutMapping("/deActive")
  public ResponseEntity<?> deActiveCategory(@Valid @RequestParam(name = "categoryId")
                                            @NotNull(message = "categoryId is required") Long categoryId) {
    return categoryService.deActiveCategory(categoryId);
  }

}
