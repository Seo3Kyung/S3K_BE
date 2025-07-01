package com.s3k.backend.global.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidatorUtilsTest {

  @DisplayName("사용 가능 닉네임 검증 테스트")
  @ParameterizedTest
  @ValueSource(strings = {"abc", "가나3", "user123가나다", "ABCDEFGHIJKL"})
  void validNicknameTest(String nickname) {
    Assertions.assertDoesNotThrow(() -> ValidatorUtils.validate("nickname", nickname));
  }

  @DisplayName("3 ~ 12자 사이의 닉네임이 아니거나 특수문자가 포함된 닉네임 테스트")
  @ParameterizedTest
  @ValueSource(strings = {"as", "asdasdasdasda", "ab#", "user name", "테스트!", "abc_def", ""})
  void tooShortNicknameTest(String shortNickname) {
    assertThatThrownBy(() -> ValidatorUtils.validate("nickname", shortNickname))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("[nickname] 유효성 검사 실패: 닉네임은 3~12자 이내의 한글·영문·숫자만 사용 가능합니다.");
  }


}
