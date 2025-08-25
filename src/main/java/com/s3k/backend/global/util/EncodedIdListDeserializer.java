package com.s3k.backend.global.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.List;

public class EncodedIdListDeserializer extends JsonDeserializer<List<Long>> {
  @Override
  @SuppressWarnings("unchecked")
  public List<Long> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JacksonException {
    return jsonParser.readValueAs(List.class).stream()
        .map(id -> EncryptUtil.decodeId(id.toString()))
        .toList();
  }
}
