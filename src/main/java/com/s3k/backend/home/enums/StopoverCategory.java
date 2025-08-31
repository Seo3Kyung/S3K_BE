package com.s3k.backend.home.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StopoverCategory {
  UNKNOWN(0, "없음"),
  RESTAURANT(1, "식당"),
  CAFE(2, "카페"),
  TOURIST_ATTRACTION(3, "관광지"),
  PARK(4, "공원"),
  SHOPPING(5, "쇼핑"),
  CULTURAL_FACILITY(6, "문화시설");

  private final int value;
  private final String desc;

  public static String getDescByValue(int value) {
    for (StopoverCategory category : values()) {
      if (category.value == value) {
        return category.desc;
      }
    }
    return null;
  }
}
