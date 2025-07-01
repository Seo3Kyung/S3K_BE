package com.s3k.backend.global.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum ValidationType {
  NICKNAME("^[a-zA-Z0-9가-힣]{3,12}$", "닉네임은 3~12자 이내의 한글·영문·숫자만 사용 가능합니다."),
  ;

  private final Pattern pattern;
  private final String errorMessage;

  ValidationType(String regex, String errorMessage) {
    this.pattern = Pattern.compile(regex);
    this.errorMessage = errorMessage;
  }

  public boolean matches(String input) {
    return pattern.matcher(input).matches();
  }

  private static final Map<String, ValidationType> typeLowerCase =
      Arrays.stream(ValidationType.values())
          .collect(Collectors.toMap(
              type -> type.name().toLowerCase(),
              Function.identity()
          ));

  public static ValidationType of(String type) {
    ValidationType validationType = typeLowerCase.get(type.toLowerCase());

    if (validationType == null) {
      log.error("{}는 지원하지 않는 검증타입입니다", type);
      throw new IllegalArgumentException("지원하지 않는 검증 타입입니다: " + type);
    }

    return validationType;
  }
}
