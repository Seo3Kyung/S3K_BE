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
  NOT_FOUND("404", "존재하지 않는 리소스"),
  METHOD_NOT_ALLOWED("405", "지원하지 않는 메소드"),
  INTERNAL_ERROR("500", "서버 오류"),

  // 상세 에러메시지
  MEMBER_VALIDATION("V01", "회원가입 유효성 검증 실패"),
  NOT_FOUND_WALKING_PATH("V02", "존재하지 않는 산책로입니다"),

  // 경유지 에러메시지
  STOPOVER_NOT_FOUND("S1", "경유지 정보를 찾을 수 없습니다.")

  ;

  private final String code;
  private final String desc;
}
