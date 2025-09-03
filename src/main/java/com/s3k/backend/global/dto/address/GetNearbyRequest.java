package com.s3k.backend.global.dto.address;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetNearbyRequest {
  private BigDecimal latitude;
  private BigDecimal longitude;
}
