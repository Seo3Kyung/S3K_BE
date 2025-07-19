package com.s3k.backend.weather.config;

import com.s3k.backend.weather.api_client.WeatherClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WeatherConfig {

  @Value("${weather.provider}")
  private String provider;

  @Bean
  public WeatherClient weatherClient(Map<String, WeatherClient> weatherClients) {
    WeatherClient weatherClient = weatherClients.get(provider);

    if (weatherClient == null) {
      throw new RuntimeException("해당 기상청 클라이언트는 존재하지 않습니다");
    }

    return weatherClient;
  }

}
