package com.s3k.backend.weather.entity;

import com.s3k.backend.weather.api_client.kma.entity.Item;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Weather {

  private static final Map<Integer, String> SKY_CONDITION_MAP = Map.of(
      1, "맑음",
      2, "구름조금",
      3, "구름많음",
      4, "흐림"
  );
  private static final Map<Integer, String> RAIN_CONDITION_MAP = Map.of(
      0, "없음",
      1, "비",
      2, "비/눈",
      3, "눈",
      4, "소나기"
  );
  private static final String DEGREE = "TMP";
  private static final String SKY = "SKY";
  private static final String RAIN_PERCENT = "POP";
  private static final String RAIN_CODE = "PTY";

  private String date;
  private String time;
  private int degree;
  private String skyCondition;
  private int rainPercent;
  private String rainCondition;

  public static Weather fromItems(WeatherDateTime weatherDateTime, List<Item> items) {

    Map<String, Item> mapByCategory = categoryMapping(items);

    int degree = parseIntFcstValue(mapByCategory, DEGREE);
    int skyCode = parseIntFcstValue(mapByCategory, SKY);
    int rainPercent = parseIntFcstValue(mapByCategory, RAIN_PERCENT);
    int rainCode = parseIntFcstValue(mapByCategory, RAIN_CODE);

    String skyCondition = SKY_CONDITION_MAP.get(skyCode);
    String rainCondition = RAIN_CONDITION_MAP.get(rainCode);

    return new Weather(
        weatherDateTime.getDate(),
        weatherDateTime.getTime(),
        degree,
        skyCondition,
        rainPercent,
        rainCondition
    );
  }

  private static Map<String, Item> categoryMapping(List<Item> items) {
    return items.stream()
        .collect(Collectors.toMap(Item::getCategory, Function.identity()));
  }

  private static int parseIntFcstValue(Map<String, Item> mapByCategory, String category) {
    Item item = mapByCategory.get(category);
    if (item == null) {
      throw new RuntimeException("카테고리가 존재하지 않습니다");
    }

    String fcstValue = item.getFcstValue();
    if (fcstValue == null) {
      throw new RuntimeException("예보값이 존재하지 않습니다");
    }

    try {
      return Integer.parseInt(fcstValue);
    } catch (NumberFormatException e) {
      throw new RuntimeException("잘못된 숫자 형식입니다");
    }
  }
}
