package com.khaofit.khaofitservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * this is a razorpay payment details related database .
 *
 * @author kousik
 */
@Entity
@Getter
@Setter
@Table(name = "payment_details", schema = "khaofit")
public class PaymentDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "user_mobile_number", nullable = false)
  private String userMobileNumber;

  @Column(name = "order_id", unique = true, nullable = false)
  private String orderId;

  @Column(name = "amount", nullable = false)
  private double amount;

  @Column(name = "order_response", columnDefinition = "TEXT")
  private String orderResponse;

  @Column(name = "event")
  private String event;

  @Column(name = "status")
  private String status;

  @Column(name = "payment_id")
  private String paymentId;

  @Column(name = "callback_response", columnDefinition = "TEXT")
  private String callBackResponse;

  @Column(name = "refund_callback_response", columnDefinition = "TEXT")
  private String refundCallBackResponse;

  @Column(name = "refund_id")
  private String refundId;

  @Column(name = "refund_status")
  private String refundStatus;

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

