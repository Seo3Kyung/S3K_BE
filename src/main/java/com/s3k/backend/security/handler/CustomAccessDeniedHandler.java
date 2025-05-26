package com.s3k.backend.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.enums.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    detailErrorLog(request);

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    String responseBody = objectMapper.writeValueAsString(
        "권한 오류: " + accessDeniedException.getMessage());

    response.getWriter().write(responseBody);
    response.getWriter().flush();
  }

  private void detailErrorLog(HttpServletRequest request) {
    Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    log.error("[권한 오류] 요청자 SNS_ID: {}, 요청자 권한: {}, 요청 경로: {}",
        member.getSnsId(),
        Role.getEnum(member.getRole()),
        request.getRequestURI());
  }
}
