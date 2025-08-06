package com.s3k.backend.file.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileStatus {
  UNKNOWN(0,"미정의"),
  TEMP(1,"임시저장"),
  UPLOAD(2,"S3 업로드"),
  DELETE(3,"삭제")
  ;
  private final Integer value;
  private final String desc;
}
