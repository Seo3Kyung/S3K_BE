package com.s3k.backend.security.jwt;

import static com.s3k.backend.security.config.SecurityConstants.ACCESS_TOKEN_SUBJECT;
import static com.s3k.backend.security.config.SecurityConstants.TOKEN_CLAIM_FIRST_KEY;

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

  public String getSnsIdFromClaim(Claims claims) {
    return claims.get(TOKEN_CLAIM_FIRST_KEY).toString();
  }

  public String extractToken(HttpServletRequest request) {
    if (request.getCookies() != null) {
      return Arrays.stream(request.getCookies())
          .filter(cookie -> ACCESS_TOKEN_SUBJECT.equals(cookie.getName()))
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
