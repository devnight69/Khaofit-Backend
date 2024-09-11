package com.khaofit.khaofitservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.Getter;
import lombok.Setter;

/**
 * this is a rating table which contains all rating for a restaurant and food item .
 *
 * @author kousik mnaik
 */
@Setter
@Getter
@Entity
@Table(name = "ratings", schema = "khaofit", indexes = {
    @Index(name = "idx_rating_food_id", columnList = "food_id"),
    @Index(name = "idx_rating_restaurant_id", columnList = "restaurant_id")
})
public class Rating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rating_id", nullable = false)
  private Long ratingId;

  @Column(name = "rating_value", nullable = false)
  private Integer ratingValue;
  @Column(name = "review", nullable = false)
  private String review;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "food_id")
  @JsonIgnore
  private FoodItem foodItem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id")
  @JsonIgnore
  private Restaurant restaurant;

  @Column(name = "active", nullable = false)
  private boolean active;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;


  @PrePersist
  private void beforeInsert() {
    this.setCreatedAt(OffsetDateTime.now(ZoneOffset.of("+05:30")));
    this.setUpdatedAt(OffsetDateTime.now(ZoneOffset.of("+05:30")));
    this.setActive(true);
  }

  @PreUpdate
  private void beforeUpdate() {
    this.setUpdatedAt(OffsetDateTime.now(ZoneOffset.of("+05:30")));
  }

  /**
   * Custom getter for createdAt to always return in IST .
   *
   * @return @ {@link OffsetDateTime}
   */
  public OffsetDateTime getCreatedAt() {
    if (createdAt != null) {
      return createdAt.withOffsetSameInstant(ZoneOffset.of("+05:30"));
    }
    return null;
  }

  /**
   * Custom getter for updatedAt to always return in IST .
   *
   * @return @ {@link OffsetDateTime}
   */
  public OffsetDateTime getUpdatedAt() {
    if (updatedAt != null) {
      return updatedAt.withOffsetSameInstant(ZoneOffset.of("+05:30"));
    }
    return null;
  }
}

