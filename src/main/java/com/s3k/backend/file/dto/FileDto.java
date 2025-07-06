package com.s3k.backend.file.dto;

import com.s3k.backend.file.adapter.FileData;
import com.s3k.backend.file.enums.FileStatus;
import com.s3k.backend.file.util.FileNamingUtil;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileDto {
  private Long fileId;
  private final String fileName;
  private final Integer fileType; // profile, attached
  private final String fileContentType;
  private final String fileExtension;
  private String filePath; // temp -> 경로, upload -> url
  private final Long fileSize;
  private Integer status; // temp, upload, delete
  private final LocalDateTime createDatetime;
  private LocalDateTime updateDatetime;
  private LocalDateTime deleteDatetime;

  private FileDto(
      String fileName,
      Integer fileType,
      String fileContentType,
      String fileExtension,
      String filePath,
      Long fileSize,
      Integer status
  ) {
    this.fileName = fileName;
    this.fileType = fileType;
    this.fileContentType = fileContentType;
    this.fileExtension = fileExtension;
    this.filePath = filePath;
    this.fileSize = fileSize;
    this.status = status;
    this.createDatetime = LocalDateTime.now();
  }

  public static FileDto of(
      FileData file,
      int fileType,
      String filepath,
      int status
  ) {
    return new FileDto(
        file.getOriginalFilename(),
        fileType,
        file.getContentType(),
        file.getExtension(),
        filepath,
        file.getSize(),
        status
    );
  }

  public void updateUploadInfo(String fileUrl) {
    this.filePath = fileUrl;
    this.status = FileStatus.UPLOAD.getValue();
    this.updateDatetime = LocalDateTime.now();
  }

  public void updateDeleteInfo(){
    this.status = FileStatus.DELETE.getValue();
    this.deleteDatetime = LocalDateTime.now();
  }
}
