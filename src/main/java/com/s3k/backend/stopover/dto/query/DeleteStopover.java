package com.s3k.backend.stopover.dto.query;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteStopover {
  private Long stopoverId;
  private LocalDateTime deleteDatetime;

  @Builder
  public DeleteStopover(Long stopoverId, LocalDateTime deleteDatetime) {
    this.stopoverId = stopoverId;
    this.deleteDatetime = deleteDatetime;
  }
}
