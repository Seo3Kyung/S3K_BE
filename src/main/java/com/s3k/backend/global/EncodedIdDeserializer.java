package com.s3k.backend.global;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Objects;

public class EncodedIdDeserializer extends JsonDeserializer<Long> {

  @Override
  public Long deserialize(JsonParser p, DeserializationContext deserializationContext)
      throws IOException, JacksonException {
    if (Objects.nonNull(p.getValueAsString())) {
      return EncryptUtil.decodeId(p.getValueAsString());
    } else {
      throw new RuntimeException("[ArithmeticException] 역직렬화 중 오류 발생.");
    }
  }
}
