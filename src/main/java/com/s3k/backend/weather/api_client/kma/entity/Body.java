package com.s3k.backend.weather.api_client.kma.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body {

  private Items items;
  private int pageNo;
  private int numOfRows;
  private int totalCount;

  // 각 값마다 묶는 작업이 있어야 한다.
  // SKY = 날씨 상태
  // POP = 강수 형태
  // PTY = 강수 확률
  // PCP = 강수량 또는 강수 없음 과 같은 한글 표시
  // 해당 날짜와 시간마다 위의 타입을 묶어서 저장
}
