package com.s3k.backend.global.address.feign;

import com.s3k.backend.global.address.dto.KakaoAddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoFeign", url = "https://dapi.kakao.com/v2/local")
public interface KakaoFeign {
  @GetMapping(value = "/geo/coord2address")
  KakaoAddressDto getAddressByCoordinate(
      @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization,
      @RequestParam("x") String x,
      @RequestParam("y") String y
  );

  @GetMapping("/search/keyword")
  KakaoAddressDto getAddressByKeyword(
      @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization,
      @RequestParam("query") String query,
//      @RequestParam("categoryGroupCode") String categoryGroupCode,
      @RequestParam("x") String x,
      @RequestParam("y") String y,
      @RequestParam("radius") Integer radius
//      @RequestParam("query") String keyword
  );
}
