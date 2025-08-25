package com.s3k.backend.global.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;

public class EncodedIdListSerializer extends JsonSerializer<List<Long>> {

  @Override
  public void serialize(List<Long> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeStartArray();
    for (Long id : value) {
      String encoded = EncryptUtil.encodeId(id).toString();
      gen.writeString(encoded);
    }
    gen.writeEndArray();
  }
}
