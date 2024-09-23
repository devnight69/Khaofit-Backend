package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.FitCoinDetails;
import com.khaofit.khaofitservice.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is a fit coin repository class .
 *
 * @author kousik manik
 */
@Repository
public interface FitCoinRepository extends JpaRepository<FitCoinDetails, Long> {

  List<FitCoinDetails> findByUserIdAndExpireFalseOrderByCreatedAtDesc(Long userId);

  List<FitCoinDetails> findByUserAndExpireFalse(Users users);

}
