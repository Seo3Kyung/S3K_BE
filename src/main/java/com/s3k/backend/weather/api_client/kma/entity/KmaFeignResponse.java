package com.s3k.backend.weather.api_client.kma.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KmaFeignResponse {

  private Header header;
  private Body body;

  // 만든 리스트에서 현재시간에 맞춰서 다음 시간부터?
  // 아니면 현재시간을 기준으로?
  public List<Item> getItems() {
    return body.getItems().getItem();
  }
}
