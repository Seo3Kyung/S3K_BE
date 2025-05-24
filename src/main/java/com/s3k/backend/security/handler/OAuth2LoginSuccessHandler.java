package com.s3k.backend.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    String front = "http://localhost:3000";
    boolean needsExtra = authentication.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("CHECK"));

    if (needsExtra) {
      // 이미 가입된 회원이라면 토큰을 만들어서 제공
    }

    String target = needsExtra
        ? front + "/signup"
        : front + "/home";

    response.sendRedirect(target);
  }
}
