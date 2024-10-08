package com.khaofit.khaofitservice.model;

import com.khaofit.khaofitservice.enums.UserGender;
import jakarta.persistence.Column;
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
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * this is a address table for storing user address details .
 *
 * @author kousik manik
 */
@Entity
@Getter
@Setter
@Table(name = "address", schema = "khaofit", indexes = {
    @Index(name = "idx_address_id", columnList = "id"),
    @Index(name = "idx_user_mobile_number", columnList = "user_mobile_number")
})
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "user_mobile_number", nullable = false)
  private String userMobileNumber;

  @Column(name = "mobile_number", nullable = false)
  private String mobileNumber;

  @Column(name = "alternate_mobile_number", nullable = false)
  private String alternateMobileNumber;

  @Column(name = "email_id")
  private String emailId;

  @Column(name = "age")
  private Integer age;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private UserGender gender;

  @Column(name = "house_flat_number", columnDefinition = "TEXT", nullable = false)
  private String houseFlatNumber;

  @Column(name = "apartment_area", columnDefinition = "TEXT", nullable = false)
  private String apartmentArea;

  @Column(name = "landmark", columnDefinition = "TEXT", nullable = false)
  private String landmark;

  @Column(name = "pin_code", nullable = false)
  private String pinCode;

  @Column(name = "address_type", nullable = false)
  private String addressType;

  @Column(name = "state", nullable = false)
  private String stateName;

  @Column(name = "district", nullable = false)
  private String districtName;

  @Column(name = "created_at", nullable = false)
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  private Date updatedAt;

  @PrePersist
  private void beforeInsert() {
    this.setCreatedAt(new Date());
    this.setUpdatedAt(new Date());
  }

  @PreUpdate
  private void beforeUpdate() {
    this.setUpdatedAt(new Date());
  }
}

