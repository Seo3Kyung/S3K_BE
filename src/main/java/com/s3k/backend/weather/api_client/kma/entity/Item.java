package com.s3k.backend.weather.api_client.kma.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Item {

  private String baseDate;
  private String baseTime;
  private String category;
  private String fcstDate;
  private String fcstTime;
  private String fcstValue;
  private int nx;
  private int ny;
}
