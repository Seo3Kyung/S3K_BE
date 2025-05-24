package com.s3k.backend.security.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenIssuer {

  @Value("${jwt.secret.key}")
  private String signingKey;

  public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";

  public String createAccessToken(Long snsId) {
    JwtBuilder jwtBuild = Jwts.builder()
        .setHeader(createHeader())
        .setClaims(createClaims(snsId))
        .setSubject(ACCESS_TOKEN_SUBJECT)
        .setExpiration(createAccessTokenExpiredDate(6, ChronoUnit.HOURS))
        .signWith(createSigningKey(), SignatureAlgorithm.HS256);
    return jwtBuild.compact();
  }

  private Map<String, Object> createHeader() {
    Map<String, Object> header = new HashMap<>();

    header.put("typ", "JWT");
    header.put("alg", "HS256");
    return header;
  }

  private Map<String, Object> createClaims(Long snsId) {
    Map<String, Object> claims = new HashMap<>();

    claims.put("snsId", snsId);
    return claims;
  }

  /**
   * 지정된 단위(amount, unit)로 만료 일시 계산
   *
   * @param amount 더할 양
   * @param unit   ChronoUnit (HOURS, DAYS 등)
   * @return 만료일시
   */
  private Date createAccessTokenExpiredDate(long amount, ChronoUnit unit) {
    Instant now = Instant.now();
    Instant expireTime = now.plus(amount, unit);
    return Date.from(expireTime);
  }

  // signingKey(base64)를 SecretKey 객체로 변환
  public Key createSigningKey() {
    byte[] keyBytes = Base64.getDecoder().decode(signingKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
