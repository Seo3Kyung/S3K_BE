package com.s3k.backend.weather.service;

import com.s3k.backend.global.address.dto.KakaoAddressDto;
import com.s3k.backend.global.address.feign.KakaoFeign;
import com.s3k.backend.weather.api_client.WeatherClient;
import com.s3k.backend.weather.api_client.kma.entity.Item;
import com.s3k.backend.weather.api_client.kma.entity.Items;
import com.s3k.backend.weather.dto.WeatherDto;
import com.s3k.backend.weather.entity.WeatherDateTime;
import com.s3k.backend.weather.util.KmaDfsConverter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
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

    // feign 결과에서 날씨 결과가 모여있는 Items로 출력
    Items currentWeather = weatherClient.getCurrentWeather(dfsPoint.nowx, dfsPoint.nowy);

    Map<WeatherDateTime, List<Item>> itemMapByTime = currentWeather.getItem().stream()
        .collect(
            Collectors.groupingBy(
                item -> new WeatherDateTime(item.getFcstDate(), item.getFcstTime()),
                TreeMap::new,
                Collectors.toList()
            )
        );

    // 날짜, 시간별 기상 데이터 추출
    return WeatherDto.Response.of(itemMapByTime);
  }
}
