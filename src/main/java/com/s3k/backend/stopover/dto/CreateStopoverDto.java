package com.s3k.backend.stopover.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.s3k.backend.global.util.EncodedIdDeserializer;
import com.s3k.backend.global.util.EncodedIdListDeserializer;
import com.s3k.backend.stopover.enums.StopoverType;
import java.util.List;

public class CreateStopoverDto {

  public record Request(
      @JsonDeserialize(using = EncodedIdDeserializer.class)
      Long walkingPathId,
      String address,
      String comment,
      StopoverType type,
      @JsonDeserialize(using = EncodedIdListDeserializer.class)
      List<Long> imageIds,
      @JsonDeserialize(using = EncodedIdDeserializer.class)
      Long representativeImageId
  ){}
}
