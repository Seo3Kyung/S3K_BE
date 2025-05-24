package com.s3k.backend.security.handler;

import com.s3k.backend.security.jwt.JwtTokenIssuer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtTokenIssuer jwtTokenIssuer;
  private static final String SIGN_UP_URL = "http://localhost:3000/signup";
  private static final String HOME_URL = "http://localhost:3000/home";
  private static final String EXTRA_USER = "CHECK";

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    String redirectURL = SIGN_UP_URL;

    if (isRegularUser(authentication)) {
      redirectURL = HOME_URL;
      Long userSnsId = getSnsIdFromAuthentication(authentication);
      String accessToken = jwtTokenIssuer.createAccessToken(userSnsId);

      ResponseCookie accessTokenCookie = createAccessTokenCookie(accessToken);
      response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    }

    response.sendRedirect(redirectURL);
  }

  private boolean isRegularUser(Authentication authentication) {
    for (GrantedAuthority auth : authentication.getAuthorities()) {
      if (!EXTRA_USER.equals(auth.getAuthority())) {
        return true;
      }
    }
    return false;
  }

  private Long getSnsIdFromAuthentication(Authentication authentication) {
    OAuth2User userInfo = (OAuth2User) authentication.getPrincipal();

    return userInfo.getAttribute("snsId");
  }

  private ResponseCookie createAccessTokenCookie(String accessToken) {
    return ResponseCookie.from(JwtTokenIssuer.ACCESS_TOKEN_SUBJECT, accessToken)
        .httpOnly(true)
        .path("/")
        .maxAge(3600)
        .build();
  }
}
