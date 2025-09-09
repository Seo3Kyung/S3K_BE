package com.s3k.backend.weather.api_client.kma;

import com.s3k.backend.weather.entity.WeatherDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class KmaRequestTimeCalculator {

  private static final List<Integer> KMA_REQUEST_TIME_LIST = List.of(
      2, 5, 8, 11, 14, 17, 20, 23
  );
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
  private static final String TIME_FORMATTER = "%02d00";

  public WeatherDateTime calculateKmaRequestTime(LocalDateTime now) {
    LocalDate date = now.toLocalDate();
    int nowHour = now.getHour();

    int searchHour = selectSearchHour(nowHour);
    if (nowHour < 1) {
      date = date.minusDays(1);
      searchHour = 23;
    }

    String searchDay = date.format(DATE_FORMATTER);
    String searchTime = String.format(TIME_FORMATTER, searchHour);

    return new WeatherDateTime(searchDay, searchTime);
  }

  private static int selectSearchHour(int nowTime) {
    return KMA_REQUEST_TIME_LIST.stream()
        .filter(time -> nowTime >= time)
        .max(Integer::compareTo)
        .orElse(0);
  }
}
