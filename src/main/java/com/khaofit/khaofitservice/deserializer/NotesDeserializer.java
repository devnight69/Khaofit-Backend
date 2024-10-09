package com.khaofit.khaofitservice.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.khaofit.khaofitservice.dto.response.OrderResponse.NotesDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom JSON deserializer for converting JSON arrays into a list of NotesDto objects.
 *
 * <p>This deserializer converts a JSON array representing notes into a List of {@link NotesDto} objects.
 * It is intended to be used with Jackson's data binding to handle the deserialization of JSON data
 * containing arrays of notes.</p>
 *
 * <p>Example usage with Jackson annotations:</p>
 * <pre>
 * {@code
 * public class MyDto {
 *     @JsonDeserialize(using = NotesDeserializer.class)
 *     private List<NotesDto> notes;
 *     // getters and setters
 * }
 * }
 * </pre>
 *
 * @see JsonDeserializer
 * @see NotesDto
 *
 * @author kousik
 */
public class NotesDeserializer extends JsonDeserializer<List<NotesDto>> {

  /**
   * Deserializes a JSON array into a list of NotesDto objects.
   *
   * @param p the JsonParser used for reading JSON content
   * @param ctxt the DeserializationContext
   * @return a List of NotesDto objects
   * @throws IOException if an I/O error occurs during deserialization
   * @throws JsonProcessingException if a JSON processing error occurs during deserialization
   */
  @Override
  public List<NotesDto> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);
    List<NotesDto> notes = new ArrayList<>();

    if (node.isObject()) {
      NotesDto note = new NotesDto();
      note.setNotes_key_1(node.get("notes_key_1").asText(null));
      note.setNotes_key_2(node.get("notes_key_2").asText(null));
      notes.add(note);
    }
    return notes;
  }
}



