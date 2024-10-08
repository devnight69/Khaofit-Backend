package com.khaofit.khaofitservice.castomvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * custom annotation for mobile number .
 *
 * @author kousik
 */
@Documented
@Constraint(validatedBy = {MobileNumberOrBlankValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface MobileNumberOrBlank {

  /**
   * mobile message .
   *
   * @return @{@link String}
   */
  String message() default "Invalid mobile number";

  /**
   * default class .
   *
   * @return @{@link Class}
   */
  Class<?>[] groups() default {};

  /**
   * extend class .
   *
   * @return @{@link Class}
   */
  Class<? extends Payload>[] payload() default {};
}

