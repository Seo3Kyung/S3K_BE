package com.s3k.backend.global.util;

import com.s3k.backend.global.enums.ValidationType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidatorUtils {

  public static void validate(String type, String value) {
    ValidationType validationType = ValidationType.of(type);

    if (!validationType.matches(value)) {
      log.error("[{}] 유효성 검사 실패: {}", type, validationType.getErrorMessage());
      throw new IllegalArgumentException(
          String.format("[%s] 유효성 검사 실패: %s", type, validationType.getErrorMessage())
      );
    }
  }
}
