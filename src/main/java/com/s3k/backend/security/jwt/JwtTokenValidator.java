package com.s3k.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenValidator {

  private final JwtTokenIssuer jwtTokenIssuer;

  public String getSnsIdFromToken(String token) {
    Claims claimsFromToken = getClaimsFromToken(token);

    return claimsFromToken.get("snsId", String.class);
  }

  public String extractToken(HttpServletRequest request, String tokenName) {
    Cookie[] cookies = request.getCookies();

    return Arrays.stream(cookies)
        .filter(cookie -> tokenName.equals(cookie.getName()))
        .findFirst()
        .map(Cookie::getValue)
        .orElse(null);
  }

  public boolean isValidToken(String token) {
    try {
      Claims claimsFromToken = getClaimsFromToken(token);
      return true;
    } catch (JwtException jwtEx) {
      log.error(jwtEx.getMessage());
      throw jwtEx;
    }
  }

  private Claims getClaimsFromToken(String token) {
    Key signingKey = jwtTokenIssuer.createSigningKey();

    Claims claims = Jwts.parserBuilder()
        .setSigningKey(signingKey)
        .build()
        .parseClaimsJws(token)
        .getBody();

    if (ObjectUtils.isEmpty(claims)) {
      throw new JwtException("유효하지 않은 토큰입니다.");
    }

    return claims;
  }
}
