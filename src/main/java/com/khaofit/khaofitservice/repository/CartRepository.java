package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is a cart repository class .
 *
 * @author kousik manik
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  List<Cart> findByUserUlid(String userUlid);

}
