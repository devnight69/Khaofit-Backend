package com.khaofit.khaofitservice.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

/**
 * StringListConverter to convert a List of Longs to a comma-separated string for database storage
 * and vice versa.
 *
 * @author kousik manik
 */
@Convert
public class LongListConverter implements AttributeConverter<List<Long>, String> {

  // Converts a List of Longs to a comma-separated String for database storage
  @Override
  public String convertToDatabaseColumn(List<Long> attribute) {
    return CollectionUtils.isEmpty(attribute)
        ? null
        : attribute.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  // Converts a comma-separated String from the database back into a List of Longs
  @Override
  public List<Long> convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.isEmpty()) {
      return null;
    }
    return Arrays.stream(dbData.split(","))
        .map(Long::valueOf)
        .collect(Collectors.toList());
  }
}
