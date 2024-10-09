package com.khaofit.khaofitservice.serviceimpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khaofit.khaofitservice.cache.GuavaCacheServiceImpl;
import com.khaofit.khaofitservice.constant.KhaoFitConstantService;
import com.khaofit.khaofitservice.dto.request.OrderData;
import com.khaofit.khaofitservice.dto.response.OrderResponse;
import com.khaofit.khaofitservice.model.PaymentDetails;
import com.khaofit.khaofitservice.repository.PaymentDetailsRepository;
import com.khaofit.khaofitservice.response.BaseResponse;
import com.khaofit.khaofitservice.service.PaymentService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * this is a payment service implementation class .
 *
 * @author kousik manik
 */
@Service
public class PaymentServiceImpl implements PaymentService {

  @Value("${payment.rzp.key}")
  private String keyId;

  @Value("${payment.rzp.secret}")
  private String secret;

  @Value("${payment.rzp.url}")
  private String baseUrl;
  @Value("${payment.rzp.version}")
  private String apiVersion;

  @Autowired
  private GuavaCacheServiceImpl guavaCacheService;

  @Autowired
  private PaymentDetailsRepository paymentDetailsRepository;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private BaseResponse baseResponse;

  private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);


  /**
   * Retrieves payment details based on the provided order ID.
   *
   * @param orderId the order ID for which to retrieve payment details.
   * @return a ResponseEntity containing the payment details if found,
   *         or a ResponseEntity containing false if no details are found.
   */
  @Override
  public ResponseEntity<?> getPaymentDetails(String orderId) {
    return baseResponse.successResponse(guavaCacheService.get(orderId));
  }

  /**
   * this method return only rzp keyId .
   *
   * @return @{@link ResponseEntity}
   */
  @Override
  public ResponseEntity<?> getRzpKey() {
    return baseResponse.successResponse(keyId);
  }


  /**
   * Creates a new payment order based on the provided order data.
   *
   * <p>This method handles the creation of a payment order. It accepts an OrderData object
   * containing the necessary information to create the order and returns a ResponseEntity
   * which contains the result of the order creation process.</p>
   *
   * @param orderData the data transfer object (DTO) containing the order details
   * @return a ResponseEntity<?> containing the response status and any additional information
   *         or errors related to the order creation
   */
  @Override
  public ResponseEntity<?> createOrder(String mobileNumber, OrderData orderData) {
    String rzpKey = Base64.getEncoder().encodeToString((keyId + ":" + secret).getBytes(StandardCharsets.UTF_8));
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      headers.add("Authorization", "Basic " + rzpKey);
      orderData.setReceipt(UUID.randomUUID().toString());
      orderData.setAmount(orderData.getAmount() * 100);
      HttpEntity<OrderData> entity = new HttpEntity<>(orderData, headers);
      String url = baseUrl + "/" + apiVersion + KhaoFitConstantService.RZP_ORDER;
      logger.info("Razorpay create order API: {}", url);
      OrderResponse resp = restTemplate.exchange(url, HttpMethod.POST, entity, OrderResponse.class).getBody();
      assert resp != null;
      PaymentDetails paymentDetails = new PaymentDetails();
      paymentDetails.setOrderId(resp.getId());
      paymentDetails.setAmount(resp.getAmount());
      paymentDetails.setUserMobileNumber(mobileNumber);
      Gson gson = new GsonBuilder()
          .serializeNulls()
          .create();
      String jsonString = gson.toJson(resp);
      paymentDetails.setOrderResponse(jsonString);
      paymentDetailsRepository.saveAndFlush(paymentDetails);
      return baseResponse.successResponse(resp);
    } catch (HttpStatusCodeException e) {
      logger.warn("Error in the response in getUnlinkedCareContextsByProviderId : {}", e.getMessage());
      logger.info("error response Status code in getUnlinkedCareContextsByProviderId : {}", e.getStatusCode());
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    } catch (Exception e) {
      logger.error("Error in the Exception in getUnlinkedCareContextsByProviderId : {}", e.getMessage());
      return baseResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

  }


  /**
   * Saves the payment status received from the payment gateway callback.
   *
   * @param response The response object containing the payment status information.
   */
  @Override
  public void savePaymentStatus(Object response) {
    // Implementation for saving the payment status will go here.
    // This could involve parsing the response object and saving relevant details
    // to the database, logging the information, or triggering other business logic.

    logger.info("callback response = {}", response.toString());

    try {
      Gson gson = new GsonBuilder()
          .serializeNulls()
          .create();
      String jsonString = gson.toJson(response);

      JSONObject jsonResponse = new JSONObject(jsonString);

      if (jsonResponse.has("contains")) {
        JSONArray array = jsonResponse.getJSONArray("contains");
        for (int i = 0; i < array.length(); i++) {
          String data = array.getString(i);
          logger.info("Data for loop = {}", data);
          if (data.equals("refund")) {
            logger.info("coming into if block");
            String event = jsonResponse.getString("event");
            JSONObject payload = jsonResponse.getJSONObject("payload");
            JSONObject payment = payload.getJSONObject("payment");
            JSONObject entity = payment.getJSONObject("entity");
            JSONObject refund = payload.getJSONObject("refund");
            JSONObject refundEntity = refund.getJSONObject("entity");

            String orderId = entity.getString("order_id");
            String status = entity.getString("status");
            String refundId = refundEntity.getString("id");
            String refundStatus = refundEntity.getString("status");

            paymentDetailsRepository.findByOrderId(orderId)
                .ifPresentOrElse(value -> {
                  value.setRefundCallBackResponse(jsonString);
                  value.setStatus(status);
                  value.setEvent(event);
                  value.setRefundId(refundId);
                  value.setRefundStatus(refundStatus);
                  paymentDetailsRepository.saveAndFlush(value);
                }, () -> {
                  // Log a message if the orderId is not found
                  logger.warn("Order ID not found: {}", orderId);
                });
            return;
          }
        }
      }

      // Extract the event
      String event = jsonResponse.getString("event");

      // Extract the order_id and status from the nested JSON structure
      JSONObject payload = jsonResponse.getJSONObject("payload");
      JSONObject payment = payload.getJSONObject("payment");
      JSONObject entity = payment.getJSONObject("entity");

      String paymentId = entity.getString("id");
      String orderId = entity.getString("order_id");
      String status = entity.getString("status");

      guavaCacheService.put(orderId, response);

      paymentDetailsRepository.findByOrderId(orderId)
          .ifPresentOrElse(value -> {
            value.setCallBackResponse(jsonString);
            value.setStatus(status);
            value.setEvent(event);
            value.setPaymentId(paymentId);
            paymentDetailsRepository.saveAndFlush(value);
          }, () -> {
            // Log a message if the orderId is not found
            logger.warn("Order ID not found: {}", orderId);
          });

    } catch (Exception e) {
      // Log the exception
      logger.error("An error occurred while processing the payment status", e);
    }
  }

}
