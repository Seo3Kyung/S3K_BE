package com.s3k.backend.weather.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Weather {

  private String date;
  private String time;
  private int degree;
  private String skyCondition;
  private int rainPercent;
  private String rainCondition;
}
