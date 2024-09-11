package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.Category;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * this is a repository class for category .
 *
 * @author kousik mnaik
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByName(String name);

  @Modifying
  @Transactional
  @Query("UPDATE Category c SET c.active = false WHERE c.categoryId = :categoryId")
  int deactivateCategory(@Param("categoryId") Long categoryId);

}
