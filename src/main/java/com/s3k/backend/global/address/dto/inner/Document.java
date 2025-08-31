package com.s3k.backend.global.address.dto.inner;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Document {

  private Address address;
  private RoadAddress roadAddress;
  private String id;
  private String addressName;
  private String roadAddressName;
  private String distance;
  private String x;
  private String y;
}
