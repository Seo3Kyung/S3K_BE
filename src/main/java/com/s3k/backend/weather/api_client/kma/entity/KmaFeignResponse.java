package com.s3k.backend.weather.api_client.kma.entity;

import com.s3k.backend.weather.entity.Weather;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KmaFeignResponse {

  private Header header;
  private Body body;

  public List<Weather> getWeatherList() {
    return body.getWeatherList();
  }

}
