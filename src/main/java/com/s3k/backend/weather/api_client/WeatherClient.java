package com.s3k.backend.weather.api_client;

import com.s3k.backend.weather.api_client.kma.entity.Items;

public interface WeatherClient {

  Items getCurrentWeather(int nx, int ny);
}
