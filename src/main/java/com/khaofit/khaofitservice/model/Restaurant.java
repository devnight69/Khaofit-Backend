package com.khaofit.khaofitservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import ulid4j.Ulid;

/**
 * this is a restaurant table for restaurant .
 *
 * @author kousik manik
 */
@Getter
@Setter
@Entity
@Table(name = "restaurants", schema = "khaofit", indexes = {
    @Index(name = "idx_restaurant_name", columnList = "name"),
    @Index(name = "idx_restaurant_location", columnList = "location")
}, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"mobile_number", "ulid"})
})
public class Restaurant {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_id", nullable = false)
  private Long restaurantId;

  @Column(name = "ulid", nullable = false)
  private String ulId;

  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "location", nullable = false, columnDefinition = "TEXT")
  private String location;
  @Column(name = "contact_info", nullable = false, columnDefinition = "TEXT")
  private String contactInfo;
  @Column(name = "mobile_number", nullable = false)
  private String mobileNumber;

  @Column(name = "rating", columnDefinition = "DOUBLE PRECISION DEFAULT 0", nullable = false)
  private Double rating;
  @Column(name = "opening_hours", nullable = false)
  private String openingHours;
  @Column(name = "active", nullable = false)
  private boolean active;

  @Column(name = "open_time", nullable = false)
  private LocalTime openTime;

  @Column(name = "close_time", nullable = false)
  private LocalTime closeTime;

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<FoodItem> foodItemList;

  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Rating> ratingList;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;


  @PrePersist
  private void beforeInsert() {
    this.setUlId(new Ulid().next());
    this.setCreatedAt(OffsetDateTime.now(ZoneOffset.of("+05:30")));
    this.setUpdatedAt(OffsetDateTime.now(ZoneOffset.of("+05:30")));
    this.setActive(true);
    this.setRating(0.0);
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

