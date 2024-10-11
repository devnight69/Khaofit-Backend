package com.khaofit.khaofitservice.dto.response;

import lombok.Data;

/**
 * AcquirerData is a data transfer object (DTO) that represents the information
 * related to the payment acquirer involved in the transaction process. The
 * payment acquirer is typically the bank or financial institution that processes
 * the payment on behalf of the merchant.
 * This class encapsulates various details about the acquirer which can be
 * essential for tracking, auditing, and troubleshooting payment transactions.
 * Fields:
 * - acquirer_id: Unique identifier for the acquirer.
 * - auth_code: Authorization code provided by the acquirer for the transaction.
 * - rrn: Retrieval Reference Number, which is a unique identifier for the transaction in the acquirer's system.
 * - txn_id: Transaction ID assigned by the acquirer for the payment.
 * - transaction_type: Type of transaction (e.g., purchase, refund).
 * - network: Payment network used for the transaction (e.g., Visa, MasterCard).
 * This DTO is integral to handling acquirer-specific data within the application,
 * ensuring that detailed acquirer information is available for various operational
 * and reporting needs.
 *
 * @author kousik
 */
@Data
public class AcquirerData {
  @SuppressWarnings("checkstyle:membername")
  private String bank_transactionId;
  private String rrn;
  @SuppressWarnings("checkstyle:membername")
  private String auth_code;
  private String arn;
  @SuppressWarnings("checkstyle:membername")
  private String transaction_id;

}

