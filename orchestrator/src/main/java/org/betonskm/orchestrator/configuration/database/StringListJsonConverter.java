package org.betonskm.orchestrator.configuration.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Converter
public class StringListJsonConverter implements AttributeConverter<List<String>, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    try {
      return attribute == null || attribute.isEmpty()
          ? "[]"
          : objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Could not convert list to JSON string.", e);
    }
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    try {
      return dbData == null
          ? Collections.emptyList()
          : objectMapper.readValue(dbData, List.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not convert JSON string to list.", e);
    }
  }
}

