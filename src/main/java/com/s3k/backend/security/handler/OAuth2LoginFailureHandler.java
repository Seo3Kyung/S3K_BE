package com.s3k.backend.security.handler;

import static com.s3k.backend.security.config.SecurityConstants.LOGIN_PATH;

import com.s3k.backend.security.util.RedirectUrlUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {


  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    String errorMessage = URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8);
    log.error("{}|{}|{}", null, "OAuth2 로그인 실패", errorMessage);

    String redirectUrl = RedirectUrlUtils.resolveFrontUrl(request);
    response.sendRedirect(redirectUrl + LOGIN_PATH);
  }
}
