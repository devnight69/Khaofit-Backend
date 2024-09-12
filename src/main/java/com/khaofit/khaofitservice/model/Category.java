package com.khaofit.khaofitservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * this is a category table which contains all category details in database .
 *
 * @author kousik manik
 */
@Getter
@Setter
@Entity
@Table(name = "categories", schema = "khaofit", indexes = {
    @Index(name = "idx_category_name", columnList = "name")
})
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id", nullable = false)
  private Long categoryId;

  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "description", nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(name = "active", nullable = false)
  private boolean active;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonBackReference
  private List<FoodItem> foodItemList;

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

