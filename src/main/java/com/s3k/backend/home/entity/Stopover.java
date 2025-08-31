package com.s3k.backend.home.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Stopover {

  private Long id;
  private Long walkingPathId;
  private String stopoverComment;
  private Integer stopoverCategory;
  private String address;

}
