package com.khaofit.khaofitservice.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaofit.khaofitservice.dto.request.CreateSubscriptionPlansRequestDto;
import com.khaofit.khaofitservice.dto.request.UserSubscriptionRequestDto;
import com.khaofit.khaofitservice.model.FitCoinDetails;
import com.khaofit.khaofitservice.model.SubscriptionPlans;
import com.khaofit.khaofitservice.model.UserSubscriptionDetails;
import com.khaofit.khaofitservice.model.Users;
import com.khaofit.khaofitservice.repository.FitCoinRepository;
import com.khaofit.khaofitservice.repository.ReferralDetailsRepository;
import com.khaofit.khaofitservice.repository.SubscriptionPlansRepository;
import com.khaofit.khaofitservice.repository.UserRepository;
import com.khaofit.khaofitservice.repository.UserSubscriptionDetailsRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.SubscriptionService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * this is a subscription service implementation class .
 *
 * @author kousik manik
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

  @Autowired
  private SubscriptionPlansRepository subscriptionPlansRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserSubscriptionDetailsRepository userSubscriptionDetailsRepository;

  @Autowired
  private ReferralDetailsRepository referralDetailsRepository;

  @Autowired
  private FitCoinRepository fitCoinRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BaseResponse baseResponse;

  private static final Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

  /**
   * this is a create subscription plan method .
   *
   * @param dto @{@link CreateSubscriptionPlansRequestDto}
   * @return @{@link ResponseEntity}
   */
  @Override
  @Transactional
  public ResponseEntity<?> createSubscriptionPlans(CreateSubscriptionPlansRequestDto dto) {
    logger.info("Attempting to create a new subscription plan with name: {}", dto.getName());

    try {
      Optional<SubscriptionPlans> optionalSubscriptionPlans = subscriptionPlansRepository
          .findByName(dto.getName().trim());

      if (optionalSubscriptionPlans.isPresent()) {
        logger.warn("Subscription plan already exists with name: {}", dto.getName());
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Subscription Plan is already present.");
      }

      SubscriptionPlans subscriptionPlans = objectMapper.convertValue(dto, SubscriptionPlans.class);
      subscriptionPlansRepository.saveAndFlush(subscriptionPlans);

      logger.info("Subscription plan created successfully with name: {}", dto.getName());
      return baseResponse.successResponse("Subscription plan added successfully.", subscriptionPlans);

    } catch (Exception e) {
      logger.error("Error occurred while creating subscription plan with name: {}. Error: {}",
          dto.getName(), e.getMessage());
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again later.");
    }
  }

  /**
   * this is a get all subscription plan method .
   *
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getAllSubscriptionPlans() {
    logger.info("Fetching all subscription plans...");

    try {
      List<SubscriptionPlans> subscriptionPlans = subscriptionPlansRepository.findByActiveTrue();

      if (subscriptionPlans.isEmpty()) {
        logger.warn("No subscription plans found.");
        return baseResponse.successResponse("No subscription plans available.", subscriptionPlans);
      }

      logger.info("Successfully retrieved {} subscription plans.", subscriptionPlans.size());
      return baseResponse.successResponse(subscriptionPlans);

    } catch (Exception e) {
      logger.error("Error occurred while fetching subscription plans: {}", e.getMessage());
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while fetching subscription plans.");
    }
  }

  /**
   * this is a get subscription plan details by ulid .
   *
   * @param ulid @{@link String}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getSubscriptionPlanByUlid(String ulid) {
    logger.info("Fetching subscription plan with ULID: {}", ulid);

    try {
      Optional<SubscriptionPlans> subscriptionPlan = subscriptionPlansRepository.findByUlIdAndActiveTrue(ulid);

      if (subscriptionPlan.isEmpty()) {
        logger.warn("Subscription plan not found for ULID: {}", ulid);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "Subscription plan not found!");
      }

      logger.info("Subscription plan found for ULID: {}", ulid);
      return baseResponse.successResponse(subscriptionPlan.get());

    } catch (Exception e) {
      logger.error("Error occurred while fetching subscription plan with ULID: {}. Error: {}", ulid, e.getMessage());
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while fetching the subscription plan.");
    }
  }

  /**
   * this is method for user subscribe a plan .
   *
   * @param dto @{@link UserSubscriptionRequestDto}
   * @return @{@link ResponseEntity}
   */
  @Override
  @Transactional
  public ResponseEntity<?> addSubscriptionForUser(UserSubscriptionRequestDto dto) {
    try {
      logger.info("Starting subscription process for user with ULID: {}", dto.getUserUlid());

      // Validate user existence
      Users user = validateUser(dto.getUserUlid());

      // Check for existing active subscription
      if (userSubscriptionDetailsRepository.existsByUserAndActiveTrue(user)) {
        logger.warn("User with ULID '{}' is already subscribed to an active plan", dto.getUserUlid());
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "User is already subscribed to an active plan.");
      }

      // Validate subscription plan existence and active status
      SubscriptionPlans subscriptionPlan = validateSubscriptionPlan(dto.getPlanUlid());

      // Calculate subscription end time
      LocalDate endDate = calculateEndDate(dto)
          .orElseThrow(() -> new IllegalArgumentException("No valid duration specified for subscription."));

      // Create and save user subscription details
      final UserSubscriptionDetails userSubscriptionDetails = createUserSubscriptionDetails(user, subscriptionPlan,
          endDate, dto.getPaymentId());
      handleReferralAndFitCoinLogic(user, subscriptionPlan);

      // Create and save FitCoin details
      updateUserFitCoins(user, subscriptionPlan.getPrice());

      logger.info("User subscription activated successfully for user with ULID: {}", dto.getUserUlid());
      return baseResponse.successResponse("User Subscription Activated Successfully", userSubscriptionDetails);

    } catch (IllegalArgumentException e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      logger.error("Error occurred while subscribing user with ULID: {}", dto.getUserUlid(), e);
      return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      logger.error("Error occurred while subscribing user with ULID: {}", dto.getUserUlid(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An unexpected error occurred. Please try again later.");
    }
  }

  /**
   * this is a method for check user is valid or not .
   *
   * @param userUlid @{@link String}
   * @return @{@link Users}
   */
  private Users validateUser(String userUlid) {
    return userRepository.findByUlId(userUlid).orElseThrow(() -> {
      logger.warn("User with ULID: {} not found", userUlid);
      return new IllegalArgumentException("User not found!");
    });
  }

  /**
   * this is a method for check subscription plan valid or not .
   *
   * @param planUlid @{@link String}
   * @return @{@link SubscriptionPlans}
   */
  private SubscriptionPlans validateSubscriptionPlan(String planUlid) {
    return subscriptionPlansRepository.findByUlIdAndActiveTrue(planUlid).orElseThrow(() -> {
      logger.warn("Subscription plan with ULID: {} not found or inactive", planUlid);
      return new IllegalArgumentException("Subscription Plan Not Found!");
    });
  }

  /**
   * this is a create user subscription details method .
   *
   * @param user             @{@link Users}
   * @param subscriptionPlan @{@link SubscriptionPlans}
   * @param endDate          @{@link LocalDate}
   * @return @{@link UserSubscriptionDetails}
   */
  private UserSubscriptionDetails createUserSubscriptionDetails(Users user, SubscriptionPlans subscriptionPlan,
                                                                LocalDate endDate, String paymentId) {
    UserSubscriptionDetails userSubscriptionDetails = new UserSubscriptionDetails();
    userSubscriptionDetails.setPaymentId(paymentId);
    userSubscriptionDetails.setUser(user);
    userSubscriptionDetails.setSubscriptionPlans(subscriptionPlan);
    userSubscriptionDetails.setSubscriptionEndTime(endDate);
    return userSubscriptionDetailsRepository.saveAndFlush(userSubscriptionDetails);
  }

  /**
   * this is a method for update fit coin details .
   *
   * @param user @{@link Users}
   * @param price @{@link Double}
   */
  private void updateUserFitCoins(Users user, Double price) {
    FitCoinDetails fitCoinDetails = new FitCoinDetails();
    fitCoinDetails.setUser(user);
    fitCoinDetails.setFitCoin(price);
    fitCoinRepository.save(fitCoinDetails);

    user.setFitCoin(user.getFitCoin() + price);
    userRepository.save(user);
  }

  /**
   * this is a calculate end date method .
   *
   * @param dto @{@link UserSubscriptionRequestDto}
   * @return @{@link Optional}
   */
  private Optional<LocalDate> calculateEndDate(UserSubscriptionRequestDto dto) {
    LocalDate startDate = LocalDate.now();
    if (dto.getDays() != null) {
      return Optional.of(startDate.plusDays(dto.getDays()));
    } else if (dto.getMonths() != null) {
      return Optional.of(startDate.plusMonths(dto.getMonths()));
    } else if (dto.getYears() != null) {
      return Optional.of(startDate.plusYears(dto.getYears()));
    }
    return Optional.empty();
  }

  /**
   * this is a handle referral and fit coin logic method .
   *
   * @param user @{@link Users}
   * @param subscriptionPlan @{@link SubscriptionPlans}
   */
  private void handleReferralAndFitCoinLogic(Users user, SubscriptionPlans subscriptionPlan) {
    referralDetailsRepository.findByUserAndIsReferralTrue(user).ifPresent(referralDetails -> {
      Optional<Users> referUsers = userRepository.findByReferralCode(referralDetails.getReferralCode());
      if (referUsers.isPresent()) {
        Integer fitCoinPercentage = referUsers.get().getFitCoinPercentage();
        Double fitCoinValue = (subscriptionPlan.getPrice() * fitCoinPercentage) / 100;

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
   * this is a get user subscription details method .
   *
   * @param ulid @{@link String}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getUserSubscriptionDetails(String ulid) {
    logger.info("Fetching user subscription details for ULID: {}", ulid);
    try {
      Optional<Users> optionalUsers = userRepository.findByUlId(ulid);

      if (optionalUsers.isEmpty()) {
        logger.warn("User with ULID: {} not found.", ulid);
        return baseResponse.errorResponse(HttpStatus.BAD_REQUEST, "User Not Found!");
      }

      Users user = optionalUsers.get();
      logger.info("User with ULID: {} found. Fetching subscription details.", ulid);

      List<UserSubscriptionDetails> userSubscriptionDetailsList = userSubscriptionDetailsRepository
          .findByUserOrderByCreatedAtDesc(user);

      if (userSubscriptionDetailsList.isEmpty()) {
        logger.info("No subscription details found for user with ULID: {}", ulid);
        return baseResponse.successResponse("No Subscription Details Found", Collections.emptyList());
      }

      logger.info("Successfully fetched {} subscription details for user with ULID: {}",
          userSubscriptionDetailsList.size(), ulid);
      return baseResponse.successResponse("Subscription Details Fetched Successfully", userSubscriptionDetailsList);
    } catch (Exception e) {
      logger.error("Error fetching subscription details for user with ULID: {}: {}", ulid, e.getMessage(), e);
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "An error occurred while fetching subscription details.");
    }
  }

}
