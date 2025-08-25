package com.s3k.backend.stopover.dto.query;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateStopover {
  private Long stopoverId;
  private String address;
  private String comment;
  private Integer typeValue;
  private List<Long> imageIds;
  private LocalDateTime updateDatetime;
}
