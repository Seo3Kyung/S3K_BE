package com.s3k.backend.file.validator;

import com.s3k.backend.global.interfaces.Validator;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Qualifier("image")
@Component
public class ImageValidator implements Validator {

  private static final Set<String> ALLOWED_MIME_TYPES = Set.of("image/jpeg", "image/png",
      "image/jpg");
  private static final Map<String, byte[][]> FILE_SIGNATURES = Map.of(
      "JPEG", new byte[][]{
          {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0},  // JFIF
          {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE1},  // EXIF
          {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xDB}   // 일반 JPEG
      },
      "PNG", new byte[][]{
          {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A}
      }
  );

  public void validate(MultipartFile image) {
    // 1. 파일 형식 검증
    validateMimeType(image);

    // 2. 파일 시그니처 검증(파일 시작 바이트 형식)
    validateFileSignature(image);
  }

  private void validateMimeType(MultipartFile file) {
    String mimeType = file.getContentType();

    if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType)) {
      throw new RuntimeException("지원하지 않는 파일 형식입니다." + mimeType);
    }
  }

  public void validateFileSignature(MultipartFile file) {
    try {
      byte[] fileBytes = file.getBytes();

      boolean isValidImage = FILE_SIGNATURES.entrySet().stream()
          .anyMatch(entry -> matchesAnySignature(fileBytes, entry.getValue()));

      if (!isValidImage) {
        throw new RuntimeException("유효하지 않은 이미지 파일입니다.");
      }

    } catch (IOException e) {
      throw new RuntimeException("파일을 읽을 수 없습니다.");
    }
  }

  private boolean matchesAnySignature(byte[] fileBytes, byte[][] signatures) {
    return Arrays.stream(signatures)
        .anyMatch(signature -> matchesSignature(fileBytes, signature));
  }

  private boolean matchesSignature(byte[] fileBytes, byte[] signature) {
    if (fileBytes.length < signature.length) {
      return false;
    }

    for (int i = 0; i < signature.length; i++) {
      if (fileBytes[i] != signature[i]) {
        return false;
      }
    }
    return true;
  }

}
