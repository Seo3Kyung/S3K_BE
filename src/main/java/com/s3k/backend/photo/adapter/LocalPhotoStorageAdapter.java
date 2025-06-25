package com.s3k.backend.photo.adapter;

import com.s3k.backend.photo.interfaces.PhotoStorage;
import com.s3k.backend.photo.util.FileNamingUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Qualifier("local")
public class LocalPhotoStorageAdapter implements PhotoStorage {

  private static final String FILE_EXTENSION_SEPARATOR = ".";

  @Value("${app.storage.local.base-dir}")
  private String baseDir;

  @Override
  public String savePhoto(MultipartFile file, String snsId) throws IOException {
    validateFile(file);

    String extension = extractFileExtension(file);
    String randomUUID = UUID.randomUUID().toString();
    String filename = FileNamingUtil.makePhotoFileName(snsId, randomUUID, extension);

    Path dirFile = Paths.get(baseDir);
    initDirectory(dirFile);

    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(
          inputStream,
          dirFile.resolve(filename),
          StandardCopyOption.REPLACE_EXISTING
      );
    }

    return filename;
  }

  private void validateFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("파일이 존재하지 않습니다");
    }
  }

  private String extractFileExtension(MultipartFile file) {
    return Optional.ofNullable(file.getOriginalFilename())
        .filter(n -> n.contains(FILE_EXTENSION_SEPARATOR))
        .map(n -> n.substring(n.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1))
        .orElse("dat");
  }

  private void initDirectory(Path dir) throws IOException {
    if (Files.notExists(dir)) {
      Files.createDirectories(dir);
    }
  }

}