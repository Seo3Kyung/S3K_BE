package com.s3k.backend.weather.service;

import com.s3k.backend.weather.api_client.WeatherClient;
import com.s3k.backend.weather.api_client.kma.entity.Item;
import com.s3k.backend.weather.api_client.kma.entity.KmaFeignResponse;
import com.s3k.backend.weather.dto.WeatherListDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final WeatherClient weatherClient;

  public WeatherListDto getWeather(int nx, int ny) {
    // feign 결과를 response로 묶기
    KmaFeignResponse currentWeather = weatherClient.getCurrentWeather(nx, ny);
    // 날짜, 시간별 기상 데이터 추출
    List<Item> items = currentWeather.getItems();
    // 날짜, 시간별 기상현황 매핑
    return WeatherListDto.of(items);
  }
}
