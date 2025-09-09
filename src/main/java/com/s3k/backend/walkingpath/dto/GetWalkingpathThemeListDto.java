package com.s3k.backend.walkingpath.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.s3k.backend.global.util.EncodedIdSerializer;

public class GetWalkingpathThemeListDto {

  public record Response(
      @JsonSerialize(using = EncodedIdSerializer.class)
      Long themeId,
      String themeName
  ){}
}
