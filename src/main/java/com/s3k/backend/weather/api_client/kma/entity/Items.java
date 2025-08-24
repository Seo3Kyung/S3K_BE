package com.s3k.backend.weather.api_client.kma.entity;

import com.s3k.backend.weather.entity.Weather;
import com.s3k.backend.weather.entity.WeatherDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Items {

  private List<Item> item;

  public List<Weather> getWeatherList() {
    Map<WeatherDateTime, List<Item>> itemGroup = this.item.stream()
        .collect(
            Collectors.groupingBy(
                item -> new WeatherDateTime(item.getFcstDate(), item.getFcstTime()),
                TreeMap::new,
                Collectors.toList()
            )
        );

    return itemGroup.entrySet().stream()
        .map(entry -> Item.fromItems(entry.getKey(), entry.getValue()))
        .toList();
  }
}
