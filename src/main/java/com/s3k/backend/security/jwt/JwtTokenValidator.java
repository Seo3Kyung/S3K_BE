package com.s3k.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenValidator {

  private final JwtTokenIssuer jwtTokenIssuer;
  private static final String AUTHORIZATION = "Authorization";

  public Long getSnsIdFromClaim(Claims claims) {
    return claims.get("snsId", Long.class);
  }

  public String extractToken(HttpServletRequest request) {
    String bearer = request.getHeader(AUTHORIZATION);
    if (bearer != null && bearer.startsWith("Bearer ")) {
      return bearer.substring(7);
    }

    if (request.getCookies() != null) {
      return Arrays.stream(request.getCookies())
          .filter(cookie -> JwtTokenIssuer.ACCESS_TOKEN_SUBJECT.equals(cookie.getName()))
          .findFirst()
          .map(Cookie::getValue)
          .orElse(null);
    }

    return null;
  }

  public Claims parseClaims(String token) {
    Key signingKey = jwtTokenIssuer.createSigningKey();

    return Jwts.parserBuilder()
        .setSigningKey(signingKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
