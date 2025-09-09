package com.s3k.backend.weather.api_client;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherFeign", url = "${weather.kma.url}")
public interface WeatherFeign {

  @GetMapping("/VilageFcstInfoService_2.0/getVilageFcst")
  Map<String, Object> getWeather(
      @RequestParam("serviceKey") String serviceKey,
      @RequestParam("pageNo") int pageNo,
      @RequestParam("numOfRows") int numOfRows,
      @RequestParam("dataType") String dataType,
      @RequestParam("base_date") String base_date,
      @RequestParam("base_time") String base_time,
      @RequestParam("nx") int nx,
      @RequestParam("ny") int ny);
}
