package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.PaymentDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is a payment details repository class .
 *
 * @author kousik manik
 */
@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

  Optional<PaymentDetails> findByOrderId(String orderId);

}
