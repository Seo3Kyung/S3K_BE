package com.s3k.backend.walkingpath.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.s3k.backend.global.util.EncodedIdListDeserializer;
import com.s3k.backend.global.util.EncodedIdSerializer;
import com.s3k.backend.stopover.enums.StopoverType;
import com.s3k.backend.walkingpath.enums.SearchOption;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class GetWalkingpathListDto {

  public record Request(
      String origin,
      String destination,
      Long walkingpathTime,
      List<StopoverType> stopoverTypeList,
      @Schema(description = "산책경로 테마 아이디 목록")
      @JsonDeserialize(using = EncodedIdListDeserializer.class)
      List<Long> walkingpathThemeList,
      SearchOption searchOption
  ){}

  public record Response(
      @JsonSerialize(using = EncodedIdSerializer.class)
      Long walkingpathId,
      Boolean like,
      String representativeImage,
      String walkingpathTitle,
      List<String> themeList,
      Long walkingTotalTime,
      String walkingpathStartName,
      String walkingpathEndName
  ){}
}
