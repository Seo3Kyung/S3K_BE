package com.s3k.backend.stopover.dto.query;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InsertStopover {
  private Long stopoverId;
  private Long walkingPathId;
  private String address;
  private String comment;
  private Integer typeValue;
  private List<Long> imageIds;
  private LocalDateTime createDatetime;

  @Builder
  public InsertStopover(
      Long walkingPathId,
      String address,
      String comment,
      Integer typeValue,
      List<Long> imageIds
  ){
    this.walkingPathId = walkingPathId;
    this.address = address;
    this.comment = comment;
    this.typeValue = typeValue;
    this.imageIds = imageIds;
    this.createDatetime = LocalDateTime.now();
  }
}
