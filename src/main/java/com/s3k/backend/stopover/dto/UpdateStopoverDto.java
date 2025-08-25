package com.s3k.backend.stopover.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.s3k.backend.global.util.EncodedIdDeserializer;
import com.s3k.backend.stopover.enums.StopoverType;
import java.util.List;

public class UpdateStopoverDto {
  public record Request(
      @JsonDeserialize(using = EncodedIdDeserializer.class)
      Long stopoverId,
      String address,
      String comment,
      StopoverType type,
      List<Long> imageIds
  ){}
}
