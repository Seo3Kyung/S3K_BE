package com.s3k.backend.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
  UNKNOWN(0, "미정의"),
  ACTIVE(1, "사용"),
  DELETE(2,"탈퇴");
  private final int value;
  private final String desc;
}
