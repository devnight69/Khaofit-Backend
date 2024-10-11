package com.khaofit.khaofitservice.enums;

/**
 * this is a order status enum class .
 *
 * @author kousik manik
 */
public enum OrderStatus {

  BOOKED("booked"),
  PREPARING("preparing"),
  ONTHEWAY("onTheWay"),
  CANCEL("cancel"),
  DELIVERED("delivered");

  private final String value;

  private OrderStatus(String value) {
    this.value = value;
  }


}
