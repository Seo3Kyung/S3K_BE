package com.s3k.backend.global.dto.address;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetNearbyResponse {
  private List<String> nearbyList;
}
