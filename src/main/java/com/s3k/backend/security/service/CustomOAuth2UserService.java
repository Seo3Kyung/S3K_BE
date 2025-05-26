package com.s3k.backend.security.service;

import com.s3k.backend.member.dto.MemberDefaultDto;
import com.s3k.backend.member.enums.Sns;
import com.s3k.backend.member.service.MemberService;
import com.s3k.backend.member.service.inner.KakaoProvider;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements
    OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final static String KAKAO = "kakao";
  private final static String GOOGLE = "google";
  private final static String NAVER = "naver";

  private final MemberService memberService;
  private final KakaoProvider kakaoProvider;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest request)
      throws OAuth2AuthenticationException {
    String registrationId = request.getClientRegistration().getRegistrationId();

    Long snsId = null;
    Sns sns = Sns.fromRegistrationId(registrationId);

    if (KAKAO.equals(registrationId)) {
      String idToken = request.getAdditionalParameters().get("id_token").toString();

      snsId = kakaoProvider.getSnsId(idToken);
    }

    // google 추가

    try {
      MemberDefaultDto.Response member = memberService.createOrGetPendingMember(snsId, sns);
      List<GrantedAuthority> authorities = List.of(
          new SimpleGrantedAuthority(member.role().name()));

      Map<String, Object> attr = Map.of("snsId", snsId);

      return new DefaultOAuth2User(authorities, attr, "snsId");
    } catch (Exception e) {
      throw new OAuth2AuthenticationException(
          new OAuth2Error("SIGN_UP_FAILED"), "회원 가입 실패", e
      );
    }
  }
}
