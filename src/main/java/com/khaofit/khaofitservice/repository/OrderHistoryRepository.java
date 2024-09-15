package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.OrderHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is a order history details repository .
 *
 * @author kousik manik
 */
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

  List<OrderHistory> findByUserUlid(String userUlid);

}
