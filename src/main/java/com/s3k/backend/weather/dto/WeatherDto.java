package com.s3k.backend.weather.dto;

import com.s3k.backend.weather.api_client.kma.entity.Item;
import com.s3k.backend.weather.entity.Weather;
import com.s3k.backend.weather.entity.WeatherDateTime;
import java.util.List;
import java.util.Map;

public class WeatherDto {

  public record Response(
      List<Weather> weathers
  ) {

    public static WeatherDto.Response of(
        Map<WeatherDateTime, List<Item>> itemMapByTime) {
      List<Weather> list = itemMapByTime.entrySet().stream()
          .map(entry -> Weather.fromItems(entry.getKey(), entry.getValue()))
          .toList();

      return new Response(list);
    }
  }

}
