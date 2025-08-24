package com.s3k.backend.global.dto.address;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetNearbyRequest {
  private String latitude;
  private String longitude;
}
