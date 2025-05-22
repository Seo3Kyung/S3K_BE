package com.s3k.backend.member.enums;

import java.util.EnumSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sns {
  UNKNOWN(0, "미정의"),
  KAKAO(1, "카카오"),
  NAVER(2, "네이버"),
  GOOGLE(3, "구글");
  private final int value;
  private final String desc;

  public static Sns getEnum(int value) {
    EnumSet<Sns> enumSet = EnumSet.allOf(Sns.class);
    for (Sns sns : enumSet) {
      if (sns.getValue() == value) {
        return sns;
      }
    }
    return Sns.UNKNOWN;
  }
}
