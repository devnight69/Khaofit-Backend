package com.khaofit.khaofitservice.serviceimpl;

import com.khaofit.khaofitservice.model.FitCoinDetails;
import com.khaofit.khaofitservice.repository.FitCoinRepository;
import com.khaofit.khaofitservice.repository.UserRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.FitCoinService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * this is a fit coin service implementation class .
 *
 * @author kousik manik
 */
@Service
public class FitCoinServiceImpl implements FitCoinService {

  @Autowired
  private FitCoinRepository fitCoinRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BaseResponse baseResponse;

  private static final Logger logger = LoggerFactory.getLogger(FitCoinServiceImpl.class);

  /**
   * this is a get fit coin history method .
   *
   * @param userId @{@link Long}
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getFitCoinHistory(Long userId) {
    logger.info("Fetching FitCoin history for userId: {}", userId);
    try {
      List<FitCoinDetails> fitCoinDetailsList = fitCoinRepository.findByUserIdAndExpireFalseOrderByCreatedAtDesc(userId);
      logger.info("Successfully fetched {} records for userId: {}", fitCoinDetailsList.size(), userId);
      return baseResponse.successResponse(fitCoinDetailsList);
    } catch (Exception e) {
      logger.error("Error fetching FitCoin history for userId: {}. Error: {}", userId, e.getMessage());
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

}
