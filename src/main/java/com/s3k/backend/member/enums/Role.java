package com.s3k.backend.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  UNKNOWN(0,"미정의"),
  MEMBER(1, "일반 유저"),
  ADMIN(2, "관리자");
  private final int value;
  private final String desc;
}
