package com.khaofit.khaofitservice.model;

import com.khaofit.khaofitservice.converter.LongListConverter;
import com.khaofit.khaofitservice.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * This is the OrderHistory entity class representing the order history table.
 * It contains information about the user's order history.
 *
 * @author  Kousik Manik
 */
@Getter
@Setter
@Entity
@Table(name = "order_history", schema = "khaofit", indexes = {
    @Index(name = "idx_order_history_user_ulid", columnList = "user_ulid"),
    @Index(name = "idx_order_history_food_item_id", columnList = "food_item_id")
})
public class OrderHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_history_id", nullable = false)
  private Long orderHistoryId;

  @Convert(converter = LongListConverter.class)
  @Column(name = "food_item_id", nullable = false, columnDefinition = "TEXT")
  private List<Long> foodItemId;

  @Column(name = "user_ulid", nullable = false)
  private String userUlid;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "order_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Column(name = "price", nullable = false)
  private double price;

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
