package com.s3k.backend.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseStatus {
  // 정상
  OK("200", "OK"),
  
  // 기본 오류
  BAD_REQUEST("400", "사용자 요청 오류"),
  INTERNAL_ERROR("500", "서버 오류"),

  // 상세 에러메시지
  MEMBER_VALIDATION("V01", "회원가입 유효성 검증 실패")
  ;
  private final String code;
  private final String desc;
}
