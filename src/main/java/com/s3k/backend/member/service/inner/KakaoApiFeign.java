package com.s3k.backend.member.service.inner;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-api", url = "https://kapi.kakao.com")
public interface KakaoApiFeign {

  @GetMapping(value = "/v2/user/me", produces = MediaType.APPLICATION_JSON_VALUE)
  Map<String, Object> getUserInfo(@RequestHeader("Authorization") String bearerToken);
}
