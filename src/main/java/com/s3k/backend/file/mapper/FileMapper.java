package com.s3k.backend.file.mapper;

import com.s3k.backend.file.dto.FileDto;
import com.s3k.backend.file.dto.query.DeleteFile;
import com.s3k.backend.file.dto.query.UpdateFile;
import com.s3k.backend.file.entity.FileEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
  void saveFile(FileDto fileDto);
  void updateFile(UpdateFile updateFile);
  void deleteFile(DeleteFile deleteFile);
  FileEntity getFile(Long fileId);
  Optional<List<FileEntity>> getFiles(List<Long> fileIds);
}
