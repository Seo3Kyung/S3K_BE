package com.s3k.backend.weather.api_client;

import com.s3k.backend.weather.api_client.kma.entity.KmaFeignResponse;

public interface WeatherClient {

  KmaFeignResponse getCurrentWeather(int nx, int ny);
}
