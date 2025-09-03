package com.s3k.backend.weather.api_client.kma;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s3k.backend.weather.api_client.WeatherClient;
import com.s3k.backend.weather.api_client.WeatherFeign;
import com.s3k.backend.weather.api_client.kma.entity.Items;
import com.s3k.backend.weather.api_client.kma.entity.KmaFeignResponse;
import com.s3k.backend.weather.entity.WeatherDateTime;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("kma")
@RequiredArgsConstructor
public class KmaWeatherClient implements WeatherClient {

  private static final int PAGE_NO = 1;
  // 검색 기준 25시간 기상청 데이터 갯수
  private static final int NUM_OF_ROWS = 12 * 25;
  private static final String DATA_TYPE = "JSON";

  @Value("${weather.kma.serviceKey}")
  private String serviceKey;

  private final ObjectMapper objectMapper;
  private final WeatherFeign weatherFeign;
  private final KmaRequestTimeCalculator kmaRequestTimeCalculator;

  public Items getCurrentWeather(int nx, int ny) {
    // 검색할 시간 탐색
    WeatherDateTime searchWeatherDateTime = kmaRequestTimeCalculator.calculateKmaRequestTime(
        LocalDateTime.now());

    Map<String, Object> kmaFeignResponseData = weatherFeign.getWeather(
        serviceKey, PAGE_NO, NUM_OF_ROWS, DATA_TYPE, searchWeatherDateTime.getDate(),
        searchWeatherDateTime.getTime(), nx, ny
    );

    KmaFeignResponse response = objectMapper.convertValue(kmaFeignResponseData.get("response"),
        KmaFeignResponse.class);

    return response.getItems();
  }
}
