package com.s3k.backend.stopover.dto.query;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InsertStopoverFile {
  private Long imageId;
  private Long stopoverId;
  private Boolean isRepresentative;

  @Builder
  public InsertStopoverFile(
      Long imageId,
      Long stopoverId,
      Boolean isRepresentative
  ){
    this.imageId = imageId;
    this.stopoverId = stopoverId;
    this.isRepresentative = isRepresentative;
  }
}
