package com.s3k.backend.home.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WalkingPath {

  private Long id;
  private String walkingPathTitle;
  private boolean imageExposure;
  private Long walkingPathStart;
  private String walkingPathStartName;
  private Long walkingPathEnd;
  private String walkingPathEndName;
  private Long walkingTotalTime;
  private Long walkingCount;
  private Long walkingDistance;
  private Long walkingKcal;
  private Long restTime;
  private String createDatetime;
  private String updateDatetime;
  private String deleteDatetime;
  private boolean isDeleted;
}
