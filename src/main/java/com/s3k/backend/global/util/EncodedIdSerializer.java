package com.s3k.backend.global.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Objects;

public class EncodedIdSerializer extends JsonSerializer<Long> {

  @Override
  public void serialize(Long value, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    if (Objects.nonNull(value)) {
      jsonGenerator.writeString(EncryptUtil.encodeId(value).toString());
    } else {
      jsonGenerator.writeNull();
    }
  }
}
