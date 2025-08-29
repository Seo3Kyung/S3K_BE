package com.s3k.backend.weather.api_client.kma.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body {

  private Items items;
  private int pageNo;
  private int numOfRows;
  private int totalCount;
}
