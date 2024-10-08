package com.khaofit.khaofitservice.castomvalidator;


import com.khaofit.khaofitservice.constant.KhaoFitConstantService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;


/**
 * custom annotation implementation for email .
 *
 * @author kousik
 */
public class MobileNumberOrBlankValidator implements ConstraintValidator<MobileNumberOrBlank, String> {

  private static final Pattern MOBILE_PATTERN = Pattern.compile(KhaoFitConstantService.MOBILE_NUMBER_REGEX);

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return StringUtils.isBlank(value) || StringUtils.isEmpty(value) || MOBILE_PATTERN.matcher(value).matches();
  }
}

