package com.s3k.backend.file.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileEntity {
  private Long fileId;
  private String fileName;
  private Integer fileType;
  private String fileContentType;
  private String fileExtension;
  private String filePath;
  private Long fileSize;
  private Integer fileStatus;
}
