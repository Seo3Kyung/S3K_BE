package com.s3k.backend.weather.service;

import com.s3k.backend.global.address.dto.KakaoAddressDto;
import com.s3k.backend.global.address.feign.KakaoFeign;
import com.s3k.backend.global.enums.ApiResponseStatus;
import com.s3k.backend.global.error.WalkiException;
import com.s3k.backend.weather.api_client.WeatherClient;
import com.s3k.backend.weather.api_client.kma.entity.KmaFeignResponse;
import com.s3k.backend.weather.dto.WeatherDto;
import com.s3k.backend.weather.util.KmaDfsConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final WeatherClient weatherClient;
  private final KakaoFeign kakaoFeign;

  @Value("${kakao.client-id}")
  private String kakaoClientId;

  public WeatherDto.Response getWeather(String locationKeyword) {
    // 해당 지역의 데이터 가져오기
    KakaoAddressDto addressByKeyword = kakaoFeign.getAddressByKeyword(
        "KakaoAK " + kakaoClientId,
        locationKeyword,
        null,
        null,
        null
    );

    // 위도 경도 -> 격자 포인트로 변경
    KmaDfsConverter.Point dfsPoint = KmaDfsConverter.getDfsPoint(addressByKeyword);

    // feign 결과를 response로 묶기
    KmaFeignResponse currentWeather = weatherClient.getCurrentWeather(dfsPoint.nowx, dfsPoint.nowy);

    if (currentWeather.getBody() == null) {
      throw new WalkiException(ApiResponseStatus.INTERNAL_ERROR);
    }

    // 날짜, 시간별 기상 데이터 추출
    return new WeatherDto.Response(currentWeather.getWeatherList());
  }
}
