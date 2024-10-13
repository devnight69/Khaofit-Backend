package com.khaofit.khaofitservice.converter;

import com.khaofit.khaofitservice.dto.request.FoodItemRegisterDto;
import com.khaofit.khaofitservice.model.FoodItem;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * this is a food item register to food item converter .
 *
 * @author kousik manik
 */
@Component
public class FoodItemRegisterDtoToFoodItemConverter implements Converter<FoodItemRegisterDto, FoodItem> {

  /**
   * this is converter method .
   *
   * @param source @{@link FoodItemRegisterDto}
   * @return @{@link FoodItem}
   */
  @Override
  public @NotNull FoodItem convert(FoodItemRegisterDto source) {

    FoodItem foodItem = new FoodItem();

    foodItem.setFoodType(source.getFoodType());
    foodItem.setDescription(source.getDescription());
    foodItem.setName(source.getName());
    foodItem.setPrice(source.getPrice());

    return foodItem;

  }
}
