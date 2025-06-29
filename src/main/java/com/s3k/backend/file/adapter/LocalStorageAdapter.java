package com.s3k.backend.file.adapter;

import com.s3k.backend.file.dto.FileDto;
import com.s3k.backend.file.enums.FileType;
import com.s3k.backend.file.interfaces.FileStorage;
import com.s3k.backend.file.util.FileNamingUtil;
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
public class LocalStorageAdapter implements FileStorage {

  private static final String FILE_EXTENSION_SEPARATOR = ".";

  @Value("${app.storage.local.base-dir}")
  private String baseDir;

  @Override
  public FileDto save(MultipartFile file, String snsId) {
    String extension = FileNamingUtil.getExtension(file);
    String randomUUID = UUID.randomUUID().toString();
    String filename = FileNamingUtil.makePhotoFileName(snsId, randomUUID, extension);

    try {
      Path directory = Paths.get(baseDir);
      if (Files.notExists(directory)) {
        Files.createDirectories(directory);
      }
      InputStream inputStream = file.getInputStream();
      Files.copy(inputStream, directory.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
      return FileDto.of(file, FileType.PROFILE.getValue(), baseDir + filename);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}