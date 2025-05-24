package com.s3k.backend.member.enums;

import java.util.EnumSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  UNKNOWN(0, "미정의"),
  MEMBER(1, "일반 유저"),
  ADMIN(2, "관리자"),
  CHECK(3, "인증 대기");
  private final int value;
  private final String desc;

  public static Role getEnum(int value) {
    EnumSet<Role> enumSet = EnumSet.allOf(Role.class);
    for (Role role : enumSet) {
      if (role.getValue() == value) {
        return role;
      }
    }
    return Role.UNKNOWN;
  }
}
