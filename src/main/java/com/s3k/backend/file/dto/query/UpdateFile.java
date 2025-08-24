package com.s3k.backend.file.dto.query;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateFile {
  private Long fileId;
  private String filePath;
  private Integer status;
  private LocalDateTime updateDatetime;

  @Builder
  public UpdateFile(
    Long fileId,
    String filePath,
    Integer fileStatus,
    LocalDateTime updateDatetime
  ){
    this.fileId = fileId;
    this.filePath = filePath;
    this.status = fileStatus;
    this.updateDatetime = updateDatetime;
  }
}
