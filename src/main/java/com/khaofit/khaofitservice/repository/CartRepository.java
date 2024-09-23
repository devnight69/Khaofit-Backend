package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.Cart;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * this is a cart repository class .
 *
 * @author kousik manik
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  List<Cart> findByUserUlidOrderByCreatedAtDesc(String userUlid);

  Optional<Cart> findByFoodItemId(Long foodId);

  @Modifying
  @Transactional
  @Query("UPDATE Cart c SET c.active = false WHERE c.cartId = :cartId")
  int deactivateCartById(@Param("cartId") Long cartId);

  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cart c WHERE"
      + " c.cartId IN :cartIds AND c.active = true")
  boolean existsByCartIdInAndActive(@Param("cartIds") List<Long> cartIds);

  // Custom query to get list of foodItemId based on a list of cartId values
  @Query("SELECT c.foodItemId FROM Cart c WHERE c.cartId IN :cartIds")
  List<Long> findFoodItemIdsByCartIds(@Param("cartIds") List<Long> cartIds);

}
