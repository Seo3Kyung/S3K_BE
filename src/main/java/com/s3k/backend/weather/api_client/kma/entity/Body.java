package com.s3k.backend.weather.api_client.kma.entity;

import com.s3k.backend.weather.entity.Weather;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body {

  private Items items;
  private int pageNo;
  private int numOfRows;
  private int totalCount;

  public List<Weather> getWeatherList() {
    return items.getWeatherList();
  }
}
