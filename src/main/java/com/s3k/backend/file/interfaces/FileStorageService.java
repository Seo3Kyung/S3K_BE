package com.s3k.backend.file.interfaces;

import com.s3k.backend.file.adapter.FileData;
import com.s3k.backend.file.dto.FileDto;
import java.io.IOException;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

//  FileDto save(String dirPath, MultipartFile file, String snsId);
//  byte[] getFile(String dirPath, String fileName);

  FileDto upload(FileData fileData) throws IOException;
  Optional<FileData> download(String path, String filename) throws IOException;
  boolean delete(String path, String filename) throws IOException;
  boolean exists(String path, String filename) throws IOException;
  Optional<FileDto> getFileInfo(String path, String filename) throws IOException;

}