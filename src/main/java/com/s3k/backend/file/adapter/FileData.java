package com.s3k.backend.file.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 데이터(MultipartFile, byte[])를 추상화하기 위한 Wrapper 클래스
 */
@Data
@Builder
public class FileData {
  private final InputStream inputStream;
  private final String originalFilename;
  private final String contentType;
  private final long size;
  private final Map<String, String> metadata;

  public static FileData fromMultipartFile(MultipartFile file) throws IOException {
    return FileData.builder()
        .inputStream(file.getInputStream())
        .originalFilename(file.getOriginalFilename())
        .contentType(file.getContentType())
        .size(file.getSize())
        .metadata(Map.of())
        .build();
  }

  public static FileData fromByteArray(byte[] bytes, String filename, String contentType) {
    return FileData.builder()
        .inputStream(new ByteArrayInputStream(bytes))
        .originalFilename(filename)
        .contentType(contentType)
        .size(bytes.length)
        .metadata(Map.of())
        .build();
  }

  public static FileData fromFile(Path filePath) throws IOException {
    String filename = filePath.getFileName().toString();
    String contentType = Files.probeContentType(filePath);

    return FileData.builder()
        .inputStream(Files.newInputStream(filePath))
        .originalFilename(filename)
        .contentType(contentType)
        .size(Files.size(filePath))
        .metadata(Map.of())
        .build();
  }

  public byte[] toByteArray() throws IOException {
    try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      inputStream.transferTo(baos);
      return baos.toByteArray();
    }
  }

  public String getExtension() {
    if (originalFilename == null) { return null; }
    return originalFilename.substring(originalFilename.lastIndexOf('.')+1);
  }
}
