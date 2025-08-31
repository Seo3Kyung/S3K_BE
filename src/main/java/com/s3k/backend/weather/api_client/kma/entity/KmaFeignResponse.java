package com.s3k.backend.weather.api_client.kma.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KmaFeignResponse {

  private Header header;
  private Body body;

  public Items getItems() {
    return body.getItems();
  }

}
