package com.khaofit.khaofitservice.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaofit.khaofitservice.dto.request.CartRequestDto;
import com.khaofit.khaofitservice.enums.UserStatus;
import com.khaofit.khaofitservice.model.Cart;
import com.khaofit.khaofitservice.repository.CartRepository;
import com.khaofit.khaofitservice.repository.FoodItemRepository;
import com.khaofit.khaofitservice.repository.UserRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.CartService;
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
 * this is a cart service implementation class .
 *
 * @author kousik manik
 */
@Service
public class CartServiceImpl implements CartService {


  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FoodItemRepository foodItemRepository;

  @Autowired
  private BaseResponse baseResponse;

  @Autowired
  private ObjectMapper objectMapper;

  private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

  /**
   * this is a add to cart method .
   *
   * @param cartRequestDto @{@link CartRequestDto}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> addToCart(CartRequestDto cartRequestDto) {
    logger.info("Received request to add food item with ID: {} to the cart for user: {}",
        cartRequestDto.getFoodItemId(), cartRequestDto.getUserUlid());

    try {
      // Check if the food item is already in the cart
      Optional<Cart> optionalCart = cartRepository.findByFoodItemId(cartRequestDto.getFoodItemId());
      if (optionalCart.isPresent()) {
        logger.warn("Food item with ID: {} is already in the cart for user: {}", cartRequestDto.getFoodItemId(),
            cartRequestDto.getUserUlid());
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "The selected food item is already in your cart.");
      }

      if (!userRepository.existsByUlIdAndStatus(cartRequestDto.getUserUlid(), UserStatus.ACTIVE)) {
        logger.warn("User Not Found Or Deactivated User = {}", cartRequestDto.getUserUlid());
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "User Not Found ");
      }

      if (!foodItemRepository.existsById(cartRequestDto.getFoodItemId())) {
        logger.warn("Food Item Is Not Found With Id = {}", cartRequestDto.getFoodItemId());
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Food Item Not Found");
      }

      // Convert DTO to entity and save it
      Cart cart = objectMapper.convertValue(cartRequestDto, Cart.class);
      logger.info("Adding food item with ID: {} to the cart for user: {}", cartRequestDto.getFoodItemId(),
          cartRequestDto.getUserUlid());

      cart = cartRepository.saveAndFlush(cart);
      logger.info("Successfully added food item with ID: {} to the cart for user: {}", cartRequestDto.getFoodItemId(),
          cartRequestDto.getUserUlid());

      return baseResponse.successResponse("Food item added to the cart successfully.", cart);

    } catch (Exception e) {
      logger.error("Error occurred while adding food item with ID: {} to the cart for user: {}. Error: {}",
          cartRequestDto.getFoodItemId(), cartRequestDto.getUserUlid(), e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while adding the food item to the cart. Please try again later.");
    }
  }


  /**
   * this is a getting all cart details for a user .
   *
   * @param userUlid @{@link String}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getAllCartDetails(String userUlid) {
    logger.info("Fetching all cart details for user: {}", userUlid);

    try {
      List<Cart> cartList = cartRepository.findByUserUlidOrderByCreatedAtDesc(userUlid);

      logger.info("Successfully fetched {} cart items for user: {}", cartList.size(), userUlid);
      return baseResponse.successResponse("Cart details fetched successfully.", cartList);

    } catch (Exception e) {
      logger.error("Error occurred while fetching cart details for user: {}. Error: {}", userUlid, e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while fetching cart details. Please try again later.");
    }
  }


  /**
   * this is a remove item from cart .
   *
   * @param cartId @{@link Long}
   * @return @{@link ResponseEntity}
   */
  @Override
  @Transactional
  public ResponseEntity<?> removeFromCart(Long cartId) {
    logger.info("Received request to remove food item from cart with cart ID: {}", cartId);

    try {
      // Check if the cart item exists before deleting
      if (!cartRepository.existsById(cartId)) {
        logger.warn("Cart item with ID: {} not found, unable to remove.", cartId);
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Cart item not found.");
      }

      // Remove the item from the cart
      cartRepository.deleteById(cartId);
      logger.info("Successfully removed food item from cart with cart ID: {}", cartId);
      return baseResponse.successResponse("Food item removed from the cart successfully.");

    } catch (Exception e) {
      logger.error("Error occurred while removing food item from cart with cart ID: {}. Error: {}",
          cartId, e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while removing the food item from the cart. Please try again later.");
    }
  }

}
