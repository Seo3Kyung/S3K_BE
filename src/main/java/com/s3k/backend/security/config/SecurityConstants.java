package com.s3k.backend.security.config;

public final class SecurityConstants {

  public static final String LOGIN_PATH = "/login";
  public static final String SIGN_UP_PATH = "/signup";
  public static final String HOME_PATH = "/home";

  // 회원 가입 중인 회원 권한
  public static final String PENDING_USER = "CHECK";

  // 쿠키 내의 토큰에 해당되는 key 값
  public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";

  // claim에 존재하는 사용자 구분값
  public static final String TOKEN_CLAIM_FIRST_KEY = "snsId";

  // OIDC에서 뽑는 데이터 값
  public static final String ID_TOKEN_CLAIM_NAME = "sub";
}
