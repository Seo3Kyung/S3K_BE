package com.s3k.backend.weather.service;

import com.s3k.backend.weather.api_client.WeatherClient;
import com.s3k.backend.weather.api_client.kma.entity.KmaFeignResponse;
import com.s3k.backend.weather.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final WeatherClient weatherClient;

  public WeatherDto.Response getWeather(int nx, int ny) {
    // feign 결과를 response로 묶기
    KmaFeignResponse currentWeather = weatherClient.getCurrentWeather(nx, ny);
    // 날짜, 시간별 기상 데이터 추출
    return new WeatherDto.Response(currentWeather.getWeatherList());
  }
}
