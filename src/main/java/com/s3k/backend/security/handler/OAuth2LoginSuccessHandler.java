package com.s3k.backend.security.handler;

import static com.s3k.backend.security.service.CustomOidcUserService.ID_TOKEN_CLAIM_NAME;

import com.s3k.backend.security.jwt.JwtTokenIssuer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtTokenIssuer jwtTokenIssuer;
  private static final String SIGN_UP_URL = "http://localhost:3000/signup";
  private static final String HOME_URL = "http://localhost:3000/home";
  private static final String PENDING_USER = "CHECK";

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    String snsId = getSnsIdFromAuthentication(authentication);

    // 신규 회원이 로그인하면 만료시간이 5분인 토큰
    String redirectURL = SIGN_UP_URL;
    String accessToken = jwtTokenIssuer.createAccessToken(snsId, 5L, ChronoUnit.MINUTES);

    if (isActiveUser(authentication)) {
      // 기존 회원이 로그인하면 만료시간이 6시간인 토큰
      redirectURL = HOME_URL;
      accessToken = jwtTokenIssuer.createAccessToken(snsId, 6L, ChronoUnit.HOURS);
    }

    ResponseCookie accessTokenCookie = createAccessTokenCookie(accessToken);
    response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    response.sendRedirect(redirectURL);
  }

  private boolean isActiveUser(Authentication authentication) {
    for (GrantedAuthority auth : authentication.getAuthorities()) {
      if (!PENDING_USER.equals(auth.getAuthority())) {
        return true;
      }
    }
    return false;
  }

  private String getSnsIdFromAuthentication(Authentication authentication) {
    OAuth2User userInfo = (OAuth2User) authentication.getPrincipal();

    return userInfo.getAttributes().get(ID_TOKEN_CLAIM_NAME).toString();
  }

  private ResponseCookie createAccessTokenCookie(String accessToken) {
    return ResponseCookie.from(JwtTokenIssuer.ACCESS_TOKEN_SUBJECT, accessToken)
        .httpOnly(true)
        .path("/")
        .maxAge(3600)
        .build();
  }
}
