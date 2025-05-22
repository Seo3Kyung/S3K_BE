package com.s3k.backend.member.service.inner;

import com.s3k.backend.member.dto.feign.KakaoTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoFeign", url = "${kakao.get-token-url}")
public interface KakaoFeign {

  @GetMapping
  KakaoTokenDto getToken(
      @RequestParam("code") String code,
      @RequestParam("client_id") String clientId,
      @RequestParam("client_secret") String clientSecret,
      @RequestParam("redirect_uri") String redirectUri,
      @RequestParam("grant_type") String grantType
  );
}
