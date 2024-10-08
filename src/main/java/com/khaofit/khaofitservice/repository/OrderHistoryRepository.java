package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.OrderHistory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * this is a order history details repository .
 *
 * @author kousik manik
 */
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

  List<OrderHistory> findByUserUlid(String userUlid);

  Page<OrderHistory> findByUserUlid(String userUlid, Pageable pageable);

  @Query("SELECT (COUNT(o) = 0) FROM OrderHistory o WHERE o.userUlid = :userUlid")
  boolean noOrdersExistByUserUlid(String userUlid);

}
