package com.s3k.backend.walkingpath.dto;

import java.util.List;

public class CreateWalkingpathDto {

  public record Request(
      Long walkingTotalTime,
      Long walkingCount,
      Long walkingDistance,
      Long walkingKcal,
      Long restTime,
      String walkingpathTitle,
      List<Long> stopoverIds,
      Boolean imageExposure,
      List<Long> themeIds
  ){
  }
}
