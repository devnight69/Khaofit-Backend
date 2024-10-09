package com.khaofit.khaofitservice.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaofit.khaofitservice.converter.FoodItemToFoodItemResponseDtoConverter;
import com.khaofit.khaofitservice.dto.request.ChangeOrderStatusRequestDto;
import com.khaofit.khaofitservice.dto.request.OrderHistoryRequestDto;
import com.khaofit.khaofitservice.dto.response.FoodItemResponseDto;
import com.khaofit.khaofitservice.dto.response.OrderHistoryResponseDto;
import com.khaofit.khaofitservice.enums.OrderStatus;
import com.khaofit.khaofitservice.model.FitCoinDetails;
import com.khaofit.khaofitservice.model.FoodItem;
import com.khaofit.khaofitservice.model.OrderHistory;
import com.khaofit.khaofitservice.model.Users;
import com.khaofit.khaofitservice.repository.CartRepository;
import com.khaofit.khaofitservice.repository.FitCoinRepository;
import com.khaofit.khaofitservice.repository.FoodItemRepository;
import com.khaofit.khaofitservice.repository.OrderHistoryRepository;
import com.khaofit.khaofitservice.repository.ReferralDetailsRepository;
import com.khaofit.khaofitservice.repository.UserRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.OrderHistoryService;
import com.khaofit.khaofitservice.utilities.StringUtils;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * this is a service implementation class for OrderHistoryService .
 *
 * @author kousik manik
 */
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

  @Autowired
  private BaseResponse baseResponse;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private OrderHistoryRepository orderHistoryRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private FitCoinRepository fitCoinRepository;

  @Autowired
  private ReferralDetailsRepository referralDetailsRepository;

  @Autowired
  private FoodItemToFoodItemResponseDtoConverter foodItemToFoodItemResponseDtoConverter;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FoodItemRepository foodItemRepository;

  private static final Logger logger = LoggerFactory.getLogger(OrderHistoryServiceImpl.class);

  /**
   * this is a create order method .
   *
   * @param dto @{@link OrderHistoryRequestDto}
   * @return @{@link ResponseEntity}
   */
  @Override
  @Transactional
  public ResponseEntity<?> createOrder(OrderHistoryRequestDto dto) {
    logger.info("Creating order with request: {}", dto);

    try {
      // Check if all carts are valid and active
      boolean cartsExist = cartRepository.existsByCartIdInAndActive(dto.getCartId());
      if (!cartsExist) {
        logger.warn("Carts not found or inactive for cart IDs: {}", dto.getCartId());
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Carts Not Found");
      }

      // Check if the user is active
      Optional<Users> optionalUsers = userRepository.findByUlId(dto.getUserUlid());
      if (optionalUsers.isEmpty()) {
        logger.warn("User not found or inactive for ULID: {}", dto.getUserUlid());
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "User Not Found");
      }

      // Fetch food item IDs from carts
      List<Long> foodItemIds = cartRepository.findFoodItemIdsByCartIds(dto.getCartId());
      logger.info("Food item IDs retrieved: {}", foodItemIds);

      // Create and save OrderHistory
      OrderHistory orderHistory = new OrderHistory();
      orderHistory.setPrice(dto.getPrice());
      orderHistory.setQuantity(dto.getQuantity());
      orderHistory.setUserUlid(dto.getUserUlid());
      orderHistory.setFoodItemId(foodItemIds);
      orderHistory.setOrderStatus(OrderStatus.BOOKED);
      if (StringUtils.isNotNullAndNotEmpty(dto.getPaymentId())) {
        orderHistory.setPaymentId(dto.getPaymentId());
      }

      orderHistoryRepository.saveAndFlush(orderHistory);

      Users users = optionalUsers.get();

      if (users.getFitCoin() >= 1) {
        double fitCoin = users.getFitCoin();
        double useFitCoin = 0.0;

        if (fitCoin > dto.getPrice()) {
          useFitCoin = dto.getPrice();
          fitCoin = fitCoin - dto.getPrice();
        } else {
          useFitCoin = fitCoin;
          fitCoin = 0.0;
        }

        FitCoinDetails fitCoinDetails = new FitCoinDetails();
        fitCoinDetails.setUser(users);
        fitCoinDetails.setFitCoin(useFitCoin);
        fitCoinDetails.setExpire(true);

        fitCoinRepository.saveAndFlush(fitCoinDetails);

        double fitCoinValue = (dto.getPrice() * users.getFitCoinPercentage()) / 100;

        boolean isOrder = orderHistoryRepository.noOrdersExistByUserUlid(users.getUlId());

        if (isOrder) {
          users.setFitCoin(fitCoin + fitCoinValue);
          handleReferralAndFitCoinLogic(users, dto.getPrice());
        } else {
          users.setFitCoin(fitCoin);
        }
        userRepository.saveAndFlush(users);
      }

      cartRepository.deleteAllById(dto.getCartId());

      logger.info("Order placed successfully for ULID: {}", dto.getUserUlid());

      return baseResponse.successResponse("Order Placed Successfully");
    } catch (Exception e) {
      logger.error("Error occurred while placing order: {}", e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while placed an order. Please try again later.");
    }
  }

  private void handleReferralAndFitCoinLogic(Users user, double price) {
    referralDetailsRepository.findByUserAndIsReferralTrue(user).ifPresent(referralDetails -> {
      Optional<Users> referUsers = userRepository.findByReferralCode(referralDetails.getReferralCode());
      if (referUsers.isPresent()) {
        Integer fitCoinPercentage = referUsers.get().getFitCoinPercentage();
        Double fitCoinValue = (price * fitCoinPercentage) / 100;

        // Create and save FitCoinDetails for referring user
        FitCoinDetails fitCoinDetails = new FitCoinDetails();
        fitCoinDetails.setUser(referUsers.get());
        fitCoinDetails.setFitCoin(fitCoinValue);
        fitCoinRepository.save(fitCoinDetails);

        // Update referring user's fit coin balance
        Users referringUser = referUsers.get();
        referringUser.setFitCoin(referringUser.getFitCoin() + fitCoinValue);
        userRepository.save(referringUser);
      }
    });
  }

  /**
   * this is a get order history method .
   *
   * @param userUlid @{@link String}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getOrderHistory(String userUlid) {
    try {
      Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC,
          "createdAt"));
      Page<OrderHistory> orderHistoryPage = orderHistoryRepository.findByUserUlid(userUlid, pageable);

      if (orderHistoryPage.isEmpty()) {
        logger.warn("No order history found for user ULID: {}", userUlid);
        return baseResponse.successResponse(List.of());
      }

      List<OrderHistoryResponseDto> response = new ArrayList<>();
      for (OrderHistory orderHistory : orderHistoryPage) {
        List<FoodItemResponseDto> foodItemResponseDtoList = new ArrayList<>();

        List<Long> foodItemIds = orderHistory.getFoodItemId();
        for (Long foodItemId : foodItemIds) {
          Optional<FoodItem> optionalFoodItem = foodItemRepository.findByIdWithDetails(foodItemId);

          if (optionalFoodItem.isEmpty()) {
            logger.warn("Food Item with ID: {} not found in order history for user ULID: {}", foodItemId, userUlid);
            continue;  // Skip adding this food item if not found
          }

          FoodItemResponseDto foodItemResponseDto = foodItemToFoodItemResponseDtoConverter
              .convert(optionalFoodItem.get());
          foodItemResponseDtoList.add(foodItemResponseDto);
        }

        OrderHistoryResponseDto orderHistoryResponseDto = getOrderHistoryResponseDto(orderHistory,
            foodItemResponseDtoList);

        response.add(orderHistoryResponseDto);
      }

      return baseResponse.successResponse(response);
    } catch (Exception e) {
      logger.error("Error occurred while retrieving order history for user ULID: {}", userUlid, e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while processing your request. Please try again later.");
    }
  }

  /**
   * this is a method for change order status .
   *
   * @param dto @{@link ChangeOrderStatusRequestDto}
   * @return @{@link ResponseEntity}
   */
  @Override
  @Transactional
  public ResponseEntity<?> changeOrderStatus(ChangeOrderStatusRequestDto dto) {
    try {
      logger.info("Received request to change order status. Order ID: {}, New Status: {}",
          dto.getOrderId(), dto.getStatus());

      Optional<OrderHistory> optionalOrderHistory = orderHistoryRepository.findById(dto.getOrderId());

      if (optionalOrderHistory.isEmpty()) {
        logger.warn("Order not found for ID: {}", dto.getOrderId());
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Order not found");
      }

      OrderHistory orderHistory = optionalOrderHistory.get();
      logger.info("Order found. Current Status: {}", orderHistory.getOrderStatus());

      // Update the order status
      orderHistory.setOrderStatus(dto.getStatus());
      logger.info("Updating order status to: {}", dto.getStatus());

      if (OrderStatus.DELIVERED.equals(dto.getStatus())) {
        orderHistory.setActive(false);
      }

      orderHistory = orderHistoryRepository.saveAndFlush(orderHistory);
      logger.info("Order status updated successfully for Order ID: {}", dto.getOrderId());

      return baseResponse.successResponse(orderHistory);
    } catch (Exception e) {
      logger.error("Error occurred while changing order status for Order ID: {}. Error: {}",
          dto.getOrderId(), e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while updating the order status.");
    }
  }

  /**
   * this is a method for create response dto .
   *
   * @param orderHistory @{@link OrderHistory}
   * @param foodItemResponseDtoList @{@link List}
   * @return @{@link OrderHistoryResponseDto}
   */
  private static @NotNull OrderHistoryResponseDto getOrderHistoryResponseDto(OrderHistory orderHistory,
                                                                             List<FoodItemResponseDto>
                                                                                 foodItemResponseDtoList) {
    OrderHistoryResponseDto orderHistoryResponseDto = new OrderHistoryResponseDto();
    orderHistoryResponseDto.setOrderHistoryId(orderHistory.getOrderHistoryId());
    orderHistoryResponseDto.setUserUlid(orderHistory.getUserUlid());
    orderHistoryResponseDto.setPaymentId(orderHistory.getPaymentId());
    orderHistoryResponseDto.setQuantity(orderHistory.getQuantity());
    orderHistoryResponseDto.setPrice(orderHistory.getPrice());
    orderHistoryResponseDto.setActive(orderHistory.isActive());
    orderHistoryResponseDto.setFoodDetails(foodItemResponseDtoList);
    orderHistoryResponseDto.setCreatedAt(orderHistory.getCreatedAt());
    orderHistoryResponseDto.setUpdatedAt(orderHistory.getUpdatedAt());
    return orderHistoryResponseDto;
  }

}
