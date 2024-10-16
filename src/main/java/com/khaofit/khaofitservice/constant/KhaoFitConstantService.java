package com.khaofit.khaofitservice.constant;

/**
 * this is a khaofit constant service .
 *
 * @author kousik manik
 */
public class KhaoFitConstantService {

  public static final String MOBILE_NUMBER_REGEX = "^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$";
  public static final String EMAIL_REGEX = "^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";


  // order related api

  public static final String RZP_ORDER = "/orders";

  public static final String RZP_CALLBACK = "/payment/status";

  // refund Realeted api

  public static final String RZP_INSTANT_REFUND = "/payments/{pay_id}/refund";


}
