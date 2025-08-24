package com.s3k.backend.file.dto.query;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteFile {
  private Long fileId;
  private Integer status;
  private LocalDateTime deleteDatetime;

  @Builder
  public DeleteFile(
      Long fileId,
      Integer fileStatus
  ){
    this.fileId = fileId;
    this.status = fileStatus;
    this.deleteDatetime = LocalDateTime.now();
  }
}
