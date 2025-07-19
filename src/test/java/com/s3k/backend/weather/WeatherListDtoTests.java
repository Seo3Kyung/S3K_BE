package com.s3k.backend.weather;

import static org.assertj.core.api.Assertions.assertThat;

import com.s3k.backend.weather.api_client.kma.entity.Item;
import com.s3k.backend.weather.dto.WeatherListDto;
import com.s3k.backend.weather.entity.Weather;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WeatherListDtoTests {

  @Test
  @DisplayName("정상 매핑 테스트")
  public void itemMappingTest() {
    Item item1Tmp = Item.builder().fcstDate("20250701").fcstTime("0900").category("TMP")
        .fcstValue("20").build();
    Item item1Sky = Item.builder().fcstDate("20250701").fcstTime("0900").category("SKY")
        .fcstValue("1")
        .build();
    Item item1Rain = Item.builder().fcstDate("20250701").fcstTime("0900").category("POP")
        .fcstValue("30")
        .build();
    Item item1RainCondition = Item.builder().fcstDate("20250701").fcstTime("0900").category("PTY")
        .fcstValue("0")
        .build();

    Item item2Tmp = Item.builder().fcstDate("20250702").fcstTime("1200").category("TMP")
        .fcstValue("30").build();
    Item item2Sky = Item.builder().fcstDate("20250702").fcstTime("1200").category("SKY")
        .fcstValue("2")
        .build();
    Item item2Rain = Item.builder().fcstDate("20250702").fcstTime("1200").category("POP")
        .fcstValue("40")
        .build();
    Item item2RainCondition = Item.builder().fcstDate("20250702").fcstTime("1200").category("PTY")
        .fcstValue("1")
        .build();

    WeatherListDto weatherListDto = WeatherListDto.of(
        List.of(item1Tmp, item1Sky, item1Rain, item1RainCondition, item2Tmp, item2Sky, item2Rain,
            item2RainCondition)
    );

    List<Weather> weathers = List.of(
        new Weather("20250701", "0900", 20, "맑음", 30, "없음"),
        new Weather("20250702", "1200", 30, "구름조금", 40, "비")
    );
    WeatherListDto tempWeather = new WeatherListDto(weathers);

    assertThat(weatherListDto)
        .usingRecursiveComparison()
        .isEqualTo(tempWeather);

  }

}
