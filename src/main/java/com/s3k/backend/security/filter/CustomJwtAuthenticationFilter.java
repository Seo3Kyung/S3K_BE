package com.s3k.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.enums.Role;
import com.s3k.backend.member.service.MemberService;
import com.s3k.backend.security.jwt.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenValidator jwtTokenValidator;
  private final MemberService memberService;
  private final ObjectMapper objectMapper;

  private static final List<String> EXCLUDED_PATHS = List.of(
      "/api/swagger",
      "/api/swagger-ui",
      "/v3/api-docs",
      "/webjars"
  );

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      
      String accessToken = jwtTokenValidator.extractToken(request);

      if (accessToken != null) {
        Claims claims = jwtTokenValidator.parseClaims(accessToken);
        String snsId = jwtTokenValidator.getSnsIdFromClaim(claims);
        Member member = memberService.getMemberDetailBySnsId(snsId);
        saveAuthentication(member);
      }

      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "[토큰 만료] ", e);
    } catch (JwtException e) {
      sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "[토큰 오류] ", e);
    } catch (Exception e) {
      sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "[서버 오류] ", e);
    }
  }

  private void saveAuthentication(Member member) {
    String roleName = Role.getEnum(member.getRole()).name();

    List<GrantedAuthority> authorities = List.of(
        new SimpleGrantedAuthority(roleName));

    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
        member, null, authorities);

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }

  private void sendError(HttpServletResponse response,
      int status, String message, Exception e) throws IOException {
    log.error("{} : {}", message, e.getMessage());
    response.setStatus(status);
    response.setContentType("application/json; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");

    Map<String, Object> body = Map.of(
        "status", status,
        "message", message,
        "reason", e.getMessage()
    );
    objectMapper.writeValue(response.getWriter(), body);
  }
}
