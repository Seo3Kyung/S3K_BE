package com.s3k.backend.stopover.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.s3k.backend.global.util.EncodedIdDeserializer;
import com.s3k.backend.global.util.EncodedIdSerializer;
import com.s3k.backend.stopover.enums.StopoverType;
import java.time.LocalDateTime;
import java.util.List;

public class GetStopoverDto {

  public record Request(
      @JsonDeserialize(using = EncodedIdDeserializer.class)
      Long stopoverId
  ){}

  public record Response(
      @JsonSerialize(using = EncodedIdSerializer.class)
      Long stopoverId,
      @JsonSerialize(using = EncodedIdSerializer.class)
      Long walkingPathId,
      String address,
      String comment,
      StopoverType type,
      List<String> imageUrls,
      LocalDateTime createDatetime
  ){}
}
