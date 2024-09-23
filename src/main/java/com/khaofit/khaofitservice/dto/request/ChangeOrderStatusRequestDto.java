package com.khaofit.khaofitservice.dto.request;

import com.khaofit.khaofitservice.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * this is a request dto for change order status .
 *
 * @author kousik manik
 */
@Data
public class ChangeOrderStatusRequestDto {

  @NotNull(message = "orderId cannot be null")
  private Long orderId;

  @NotNull(message = "status cannot be null")
  private OrderStatus status;

}
