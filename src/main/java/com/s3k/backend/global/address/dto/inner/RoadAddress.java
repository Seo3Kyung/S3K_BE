package com.s3k.backend.global.address.dto.inner;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoadAddress {
  private String addressName;
  private String region1depthName;
  private String region2depthName;
  private String region3depthName;
  private String roadName;
  private String undergroundYn;
  private String mainBuildingNo;
  private String subBuildingNo;
  private String buildingName;
  private String zoneNo;
}
