package com.khaofit.khaofitservice.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Custom JSON deserializer for converting Unix timestamps to formatted date and time strings.
 *
 * <p>This deserializer converts a Unix timestamp (seconds since epoch) from a JSON field into
 * a human-readable date and time string in the format "yyyy-MM-dd HH:mm:ss". It is intended to
 * be used with Jackson's data binding to handle the deserialization of JSON data containing
 * Unix timestamps.</p>
 *
 * @author kousik
 */
public class TimestampDeserializer extends JsonDeserializer<String> {

  /**
   * Deserializes a Unix timestamp from JSON and converts it to a formatted date and time string.
   *
   * <p>This method reads a Unix timestamp (seconds since epoch) from the JSON parser, converts it
   * into a LocalDateTime object, and then formats it into a human-readable date and time string
   * in the format "yyyy-MM-dd HH:mm:ss".</p>
   *
   * @param p the JsonParser used for reading JSON content
   * @param ctxt the DeserializationContext providing additional information for deserialization
   * @return a formatted date and time string in the "yyyy-MM-dd HH:mm:ss" format
   * @throws IOException if an I/O error occurs during deserialization
   * @throws JsonProcessingException if a JSON processing error occurs during deserialization
   */
  @Override
  public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    long timestamp = p.getLongValue();
    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return dateTime.format(formatter);
  }

}


