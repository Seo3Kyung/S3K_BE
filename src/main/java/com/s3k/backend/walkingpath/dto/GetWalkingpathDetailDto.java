package com.s3k.backend.walkingpath.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.s3k.backend.global.util.EncodedIdSerializer;
import java.util.List;

public class GetWalkingpathDetailDto {

  public record Response(
      @JsonSerialize(using = EncodedIdSerializer.class)
      Long walkingpathId,
      String walkingpathTitle,
      List<String> themeList,
      String walkingpathStartName,
      String walkingpathEndName,
      Long walkingDistance,
      Integer stopoverCount,
      Long walkingTime,
      Boolean like,
      String representativeUrl,
      List<String> stopoverUrls
  ){}
}
