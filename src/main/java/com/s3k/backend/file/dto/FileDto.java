package com.s3k.backend.file.dto;

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
  private final LocalDateTime createdDatetime;
  private LocalDateTime updatedDatetime;
  private LocalDateTime deletedDatetime;

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
    this.createdDatetime = LocalDateTime.now();
  }

  public static FileDto of(
      MultipartFile file,
      int fileType,
      String filePath
  ) {
    return new FileDto(
        file.getOriginalFilename(),
        fileType,
        file.getContentType(),
        FileNamingUtil.getExtension(file),
        filePath,
        file.getSize(),
        FileStatus.TEMP.getValue()
    );
  }

  public void updateUploadInfo(String fileUrl) {
    this.filePath = fileUrl;
    this.status = FileStatus.UPLOAD.getValue();
    this.updatedDatetime = LocalDateTime.now();
  }

  public void updateDeleteInfo(){
    this.status = FileStatus.DELETE.getValue();
    this.deletedDatetime = LocalDateTime.now();
  }
}
