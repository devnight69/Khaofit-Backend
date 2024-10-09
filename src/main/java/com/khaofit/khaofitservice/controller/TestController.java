package com.khaofit.khaofitservice.controller;


import com.khaofit.khaofitservice.cache.GuavaCacheServiceImpl;
import com.khaofit.khaofitservice.dto.request.OrderData;
import com.khaofit.khaofitservice.dto.response.OrderResponse;
import com.khaofit.khaofitservice.dto.response.ResponseDto;
import com.khaofit.khaofitservice.service.PaymentService;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

/**
 * this is test class .
 *
 * @author kousik
 */
@RestController
@RequestMapping("/api")
public class TestController {

  @Value("${payment.rzp.key}")
  private String keyId;

  @Autowired
  private ViewResolver viewResolver;

  @Autowired
  private SpringTemplateEngine templateEngine;

  @Autowired
  private PaymentService rzpService;

  @Autowired
  private GuavaCacheServiceImpl guavaCacheService;

  /**
   * this is a simple controller class .
   *
   * @param model @{@link Model}
   * @param amount @{@link String}
   * @return @{@link ResponseEntity}
   */
  @GetMapping("/generatePaymentForm/{amount}/{mobile}")
  public ResponseEntity<String> generatePaymentForm(Model model, @PathVariable("amount") String amount,
                                                    @PathVariable String mobile) {
    // Create the order and get the response
    OrderData orderData = new OrderData();
    orderData.setAmount(Double.parseDouble(amount) * 100);
    orderData.setReceipt("bakaiti");
    ResponseEntity<?> response = rzpService.createOrder(mobile, orderData);

    // Check if the order creation was successful
    if (response.getStatusCode() == HttpStatus.OK) {
      // Extract order details from the response
      ResponseDto responseDto = (ResponseDto) response.getBody();
      assert responseDto != null;
      OrderResponse orderResponse = (OrderResponse) responseDto.getData();
      if (orderResponse != null) {
        // Prepare options for the Razorpay payment form
        Map<String, Object> options = new HashMap<>();
        options.put("key", keyId); // Replace with your Razorpay Key ID
        options.put("amount", orderResponse.getAmount()); // Amount in paise
        options.put("currency", orderResponse.getCurrency());
        options.put("name", "Ambula");
        options.put("description", "Test Transaction");
        options.put("order_id", orderResponse.getId());

        // Prepare the model with options
        model.addAttribute("options", options);

        // Create a context with a specific Locale
        Locale locale = Locale.getDefault(); // or any other locale you want to use
        Context context = new Context(locale);
        context.setVariables(model.asMap()); // Pass model attributes to context

        String paymentFormHtml = templateEngine.process("paymentForm", context);

        return ResponseEntity.ok(paymentFormHtml);
      }
    }
    // If order creation fails or response is not as expected, return error
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating payment form");
  }


  @PostMapping("/save")
  public void put(@RequestParam String key, @RequestBody Object value) {
    guavaCacheService.put(key, value);
  }

  @GetMapping("/get")
  public Object get(@RequestParam String key) {
    return guavaCacheService.get(key);
  }

  @DeleteMapping("/remove")
  public void remove(@RequestParam String key) {
    guavaCacheService.remove(key);
  }

}
