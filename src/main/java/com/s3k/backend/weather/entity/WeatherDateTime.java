package com.s3k.backend.weather.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class WeatherDateTime implements Comparable<WeatherDateTime> {

  String date;
  String time;

  @Override
  public int compareTo(@NotNull WeatherDateTime otherWeatherDateTime) {
    int compareDate = this.date.compareTo(otherWeatherDateTime.date);

    if (compareDate == 0) {
      return time.compareTo(otherWeatherDateTime.getTime());
    }

    return compareDate;
  }
}
