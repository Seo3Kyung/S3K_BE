package com.s3k.backend.stopover;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.s3k.backend.global.util.EncodedIdDeserializer;
import com.s3k.backend.global.util.EncodedIdSerializer;
import java.util.List;

public class GetStopoverDto {

  public record Request(
      @JsonDeserialize(using = EncodedIdDeserializer.class)
      Long stopoverId
  ){}

  public record Response(
      @JsonSerialize(using = EncodedIdSerializer.class)
      Long stopoverId,
      String address,
      String comment,
      StopoverType type,
      List<String> imageUrls
  ){}
}
