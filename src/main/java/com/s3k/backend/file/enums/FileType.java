package com.s3k.backend.file.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
  UNKNOWN(0, "미정의"),
  PROFILE(1, "프로필 이미지"),
  ATTACH(2,"첨부 파일")
  ;
  private final Integer value;
  private final String desc;
}
