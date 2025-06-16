package com.s3k.backend.member.service.inner;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.s3k.backend.member.dto.feign.KakaoTokenDto;
//import java.util.Base64;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KakaoProvider {
//
//  private final KakaoFeign kakaoFeign;
//  private final String clientId;
//  private final String clientSecret;
//  private final String redirectUrl;
//  private final String grantType;
//
//  public KakaoProvider(
//      KakaoFeign kakaoFeign,
//      @Value("${kakao.client-id}") String clientId,
//      @Value("${kakao.client-secret}") String clientSecret,
//      @Value("${kakao.redirect-url}") String redirectUrl
//  ) {
//    this.kakaoFeign = kakaoFeign;
//    this.clientId = clientId;
//    this.clientSecret = clientSecret;
//    this.redirectUrl = redirectUrl;
//    this.grantType = "authorization_code";
//  }
//
//  public KakaoTokenDto getTokenInfo(String authToken) {
//    // TODO : 예외처리 공통화 후 수정 예정.
//    try {
//      return kakaoFeign.getToken(
//          authToken,
//          clientId,
//          clientSecret,
//          redirectUrl,
//          grantType
//      );
//    } catch (Exception e) {
//      System.out.println(e.getMessage());
//    }
//
//    return null;
//  }
//
//  public Long getSnsId(String idToken) {
//    // TODO : 예외처리 공통화 후 수정 예정.
//    try {
//      if (idToken == null) {
//        return null;
//      }
//      String decodedPayload = decodePayload(idToken);
//      return new ObjectMapper().readTree(decodedPayload).get("sub").asLong();
//    } catch (Exception e) {
//      System.out.println(e.getMessage());
//    }
//    return null;
//  }
//
//  public String decodePayload(String idToken) {
//    String payload = idToken.substring(idToken.indexOf(".") + 1, idToken.lastIndexOf("."));
//    return new String(Base64.getDecoder().decode(payload));
//  }
//}
