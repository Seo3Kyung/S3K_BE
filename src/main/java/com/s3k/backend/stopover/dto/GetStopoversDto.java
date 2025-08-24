package com.s3k.backend.stopover.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.s3k.backend.global.util.EncodedIdDeserializer;

public class GetStopoversDto {
  public record Request(
      @JsonDeserialize(using= EncodedIdDeserializer.class)
      Long walkingPathId
  ){}
}
