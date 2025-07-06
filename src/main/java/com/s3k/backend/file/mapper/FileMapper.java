package com.s3k.backend.file.mapper;

import com.s3k.backend.file.dto.FileDto;
import com.s3k.backend.file.entity.FileEntity;
import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
  void saveFile(FileDto fileDto);
  void uploadFile(Long fileId, String filePath, Integer status, LocalDateTime uploadDateTime);
  void deleteFile(Long fileId, Integer status, LocalDateTime deleteDateTime);
  FileEntity getFile(Long fileId);
}
