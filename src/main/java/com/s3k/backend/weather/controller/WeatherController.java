package com.s3k.backend.weather.controller;

import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.weather.dto.WeatherListDto;
import com.s3k.backend.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/weathers")
@RestController
@RequiredArgsConstructor
public class WeatherController {

  private final WeatherService weatherService;

  @GetMapping()
  public ApisResponse<WeatherListDto> getWeather(
      @RequestParam int nx,
      @RequestParam int ny
  ) {
    return ApisResponse.ok(weatherService.getWeather(nx, ny));
  }
}
