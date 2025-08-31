package com.s3k.backend.home.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WalkingCategory {
  UNKNOWN(0, "없음");

  private final int value;
  private final String desc;
}
