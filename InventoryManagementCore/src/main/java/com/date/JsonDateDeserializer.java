package com.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;

public class JsonDateDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(
      JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
    String string = jsonParser.getText();
    return LocalDateTime.parse(string.replace("T", " "));
  }

}
