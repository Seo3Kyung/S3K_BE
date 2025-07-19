package com.s3k.backend.weather.dto;

import com.s3k.backend.weather.api_client.kma.entity.Item;
import com.s3k.backend.weather.entity.Weather;
import com.s3k.backend.weather.entity.WeatherDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeatherListDto {

  private final List<Weather> weatherList;

  public static WeatherListDto of(List<Item> items) {
    Map<WeatherDateTime, List<Item>> itemGroup = items.stream()
        .collect(
            Collectors.groupingBy(
                item -> new WeatherDateTime(item.getFcstDate(), item.getFcstTime()),
                TreeMap::new,
                Collectors.toList()
            )
        );

    List<Weather> list = itemGroup.entrySet().stream()
        .map(entry -> Weather.fromItems(entry.getKey(), entry.getValue()))
        .toList();

    return new WeatherListDto(list);
  }
}
