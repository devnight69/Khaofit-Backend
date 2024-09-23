package com.khaofit.khaofitservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.Getter;
import lombok.Setter;

/**
 * This is the Cart entity class representing the cart table.
 * It contains information about the cart items for users.
 *
 * @author kousik manik
 */
@Getter
@Setter
@Entity
@Table(name = "cart", schema = "khaofit", indexes = {
    @Index(name = "idx_cart_user_ulid", columnList = "user_ulid"),
    @Index(name = "idx_cart_food_item_id", columnList = "food_item_id")
})
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id", nullable = false)
  private Long cartId;

  @Column(name = "food_item_id", nullable = false)
  private Long foodItemId;

  @Column(name = "user_ulid", nullable = false)
  private String userUlid;

  @Column(name = "active", nullable = false)
  private boolean active;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  @PrePersist
  private void beforeInsert() {
    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.of("+05:30"));
    this.createdAt = now;
    this.updatedAt = now;
    this.active = true;
  }

  @PreUpdate
  private void beforeUpdate() {
    this.updatedAt = OffsetDateTime.now(ZoneOffset.of("+05:30"));
  }

  /**
   * Custom getter for createdAt to always return in IST.
   *
   * @return {@link OffsetDateTime}
   */
  public OffsetDateTime getCreatedAt() {
    return (createdAt != null) ? createdAt.withOffsetSameInstant(ZoneOffset.of("+05:30")) : null;
  }

  /**
   * Custom getter for updatedAt to always return in IST.
   *
   * @return {@link OffsetDateTime}
   */
  public OffsetDateTime getUpdatedAt() {
    return (updatedAt != null) ? updatedAt.withOffsetSameInstant(ZoneOffset.of("+05:30")) : null;
  }
}
