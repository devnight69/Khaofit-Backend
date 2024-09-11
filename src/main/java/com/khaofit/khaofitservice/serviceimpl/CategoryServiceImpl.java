package com.khaofit.khaofitservice.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaofit.khaofitservice.dto.request.CategoryRequestDto;
import com.khaofit.khaofitservice.model.Category;
import com.khaofit.khaofitservice.repository.CategoryRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.CategoryService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * this is a category service implementation class .
 *
 * @author kousik mnaik
 */
@Service
public class CategoryServiceImpl implements CategoryService {


  @Autowired
  private BaseResponse baseResponse;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

  /**
   * this is a method for create category .
   *
   * @param dto @{@link CategoryRequestDto}
   * @return @{@link ResponseEntity}
   */
  @Override
  @Transactional
  public ResponseEntity<?> createCategory(CategoryRequestDto dto) {
    logger.info("Starting to create a new category with name: {}", dto.getName().trim());
    try {
      dto.setName(dto.getName().trim());
      // Check if category with the given name already exists
      Optional<Category> optionalCategory = categoryRepository.findByName(dto.getName());

      if (optionalCategory.isPresent()) {
        logger.warn("Category with name '{}' already exists.", dto.getName().trim());
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Category already exists");
      }

      // Map DTO to Category entity
      Category category = objectMapper.convertValue(dto, Category.class);

      // Save the new category to the database
      category = categoryRepository.saveAndFlush(category);

      logger.info("Category '{}' registered successfully with ID: {}", category.getName(), category.getCategoryId());
      return baseResponse.successResponse("Category registered successfully", category);

    } catch (IllegalArgumentException e) {
      // Handle specific exception from ObjectMapper
      logger.error("Error converting DTO to entity for category: {}", e.getMessage());
      return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Invalid data format for category");

    } catch (Exception e) {
      // Log any unexpected exceptions
      logger.error("An unexpected error occurred while creating category: {}", e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again.");
    }
  }

  /**
   * this is method for return all category list .
   *
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getAllCategoryList() {
    try {
      List<Category> categorieList = categoryRepository.findAll();
      return baseResponse.successResponse(categorieList);
    } catch (Exception e) {
      logger.error("An unexpected error occurred while retrieving category List: {}", e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again.");
    }
  }

  /**
   * this is a method for get category details by id .
   *
   * @param categoryId @{@link Long}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getCategoryDetailsById(Long categoryId) {
    logger.info("Fetching category details for ID: {}", categoryId);

    try {
      Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

      if (optionalCategory.isEmpty()) {
        logger.warn("Category not found for ID: {}", categoryId);
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Category not found");
      }

      logger.info("Category details fetched successfully for ID: {}", categoryId);
      return baseResponse.successResponse("Category details fetched successfully", optionalCategory.get());

    } catch (Exception e) {
      logger.error("An unexpected error occurred while fetching category details for ID: {}", categoryId, e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again.");
    }
  }

  /**
   * this is a method for de active category .
   *
   * @param categoryId @{@link Long}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> deActiveCategory(Long categoryId) {
    try {
      int updatedRows = categoryRepository.deactivateCategory(categoryId);

      if (updatedRows == 0) {
        logger.warn("Category with ID {} not found for deactivation.", categoryId);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Category not found");
      }

      logger.info("Category with ID {} successfully deactivated.", categoryId);
      return baseResponse.successResponse("Category deactivated successfully");

    } catch (Exception e) {
      logger.error("Error occurred while deactivating category with ID {}: {}", categoryId, e.getMessage());
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again.");
    }
  }

}
