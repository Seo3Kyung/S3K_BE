package com.s3k.backend.global.address.dto.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.apache.ibatis.annotations.Property;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Address {
  private String addressName;
  @JsonProperty("region_1depth_name")
  private String region1depthName;
  @JsonProperty("region_2depth_name")
  private String region2depthName;
  @JsonProperty("region_3depth_name")
  private String region3depthName;
  private String mountainYn;
  private String mainAddressNo;
  private String subAddressNo;
}
