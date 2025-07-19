package com.s3k.backend.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.s3k.backend.weather.api_client.kma.entity.Item;
import com.s3k.backend.weather.entity.Weather;
import com.s3k.backend.weather.entity.WeatherDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class WeatherTests {

  @Nested
  @DisplayName("예외 케이스 테스트")
  class ExceptionTests {

    @Test
    @DisplayName("있어야할 카테고리가 없으면 예외 발생")
    void 있어야할_카테고리가_없으면_예외_발생() {
      WeatherDateTime weatherDateTime = new WeatherDateTime("20250701", "1200");
      Item tmpItem = Item.builder()
          .category("TTT")
          .fcstValue("1")
          .build();

      assertThatThrownBy(() -> Weather.fromItems(weatherDateTime, List.of(tmpItem)))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("카테고리가 존재하지 않습니다");
    }

    @Test
    @DisplayName("fcstValue(예보값)이 없으면 예외 발생")
    void 예보값이_없으면_예외_발생() {
      WeatherDateTime weatherDateTime = new WeatherDateTime("20250701", "1200");
      Item tmpItem = Item.builder()
          .category("TMP")
          .fcstValue(null)
          .build();

      assertThatThrownBy(() -> Weather.fromItems(weatherDateTime, List.of(tmpItem)))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("예보값이 존재하지 않습니다");
    }

    @Test
    @DisplayName("예보값이 숫자가 아니면 예외 발생")
    void 예보값이_숫자가_아니면_예외_발생() {
      WeatherDateTime weatherDateTime = new WeatherDateTime("20250701", "1200");
      Item tmpItem = Item.builder()
          .category("TMP")
          .fcstValue("asd")
          .build();

      assertThatThrownBy(() -> Weather.fromItems(weatherDateTime, List.of(tmpItem)))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("잘못된 숫자 형식입니다");
    }
  }


  @Nested
  @DisplayName("정상 테스트")
  class SuccessTests {

    @Test
    @DisplayName("옳바른 테스트")
    void 옳바른_테스트() {
      WeatherDateTime weatherDateTime = new WeatherDateTime("20250701", "1200");
      Item tmpItem = Item.builder().category("TMP").fcstValue("25").build();
      Item skyItem = Item.builder().category("SKY").fcstValue("1").build();
      Item popItem = Item.builder().category("POP").fcstValue("30").build();
      Item ptyItem = Item.builder().category("PTY").fcstValue("4").build();

      Weather weather = Weather.fromItems(weatherDateTime,
          List.of(tmpItem, skyItem, popItem, ptyItem));

      assertAll("반환 결과",
          () -> assertThat(weather.getDate()).isEqualTo("20250701"),
          () -> assertThat(weather.getTime()).isEqualTo("1200"),
          () -> assertThat(weather.getDegree()).isEqualTo(25),
          () -> assertThat(weather.getSkyCondition()).isEqualTo("맑음"),
          () -> assertThat(weather.getRainPercent()).isEqualTo(30),
          () -> assertThat(weather.getRainCondition()).isEqualTo("소나기")
      );

    }
  }


}
