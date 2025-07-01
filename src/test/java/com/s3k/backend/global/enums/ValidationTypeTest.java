package com.s3k.backend.global.enums;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidationTypeTest {

  @DisplayName("type을 찾는것은 대소문자 구분없이 진행가능")
  @ParameterizedTest
  @CsvSource({
      "nickname, NICKNAME",
      "NICKNAME, NICKNAME",
  })
  void validTypeTest(String type, ValidationType expected) {
    assertEquals(expected, ValidationType.of(type));
  }

  @DisplayName("ValidationType에 없는 Type은 예외 발생")
  @ParameterizedTest
  @ValueSource(strings = {"invalidType"})
  void invalidTypeTest(String invalidType) {
    assertThatThrownBy(() -> ValidationType.of(invalidType))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("지원하지 않는 검증 타입입니다: " + invalidType);
  }


}
