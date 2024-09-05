package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.ReferralDetails;
import com.khaofit.khaofitservice.model.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is a repository class for referralDetails .
 *
 * @author kousik manik
 */
@Repository
public interface ReferralDetailsRepository extends JpaRepository<ReferralDetails, Long> {

  List<ReferralDetails> findByReferralCode(String referralCode);

  Optional<ReferralDetails> findByUserAndIsReferralTrue(Users user);

}
