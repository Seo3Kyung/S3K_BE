package com.s3k.backend.weather.dto;

import com.s3k.backend.weather.entity.Weather;
import java.util.List;

public class WeatherDto {

  public record Response(
      List<Weather> weathers
  ) {

  }

}
