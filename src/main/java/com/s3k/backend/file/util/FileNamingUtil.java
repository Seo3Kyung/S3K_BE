package com.s3k.backend.file.util;

import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileNamingUtil {

  private static final String SEP = "_";
  private static final String EXT_SEPARATOR = ".";

 public static String getExtension(MultipartFile file) {
   return Objects.requireNonNull(file.getOriginalFilename())
       .substring(file.getOriginalFilename().lastIndexOf('.')+1);
 }

  public static String makePhotoFileName(
      final String snsId,
      final String extension
  ) {
    return snsId + SEP + UUID.randomUUID() + EXT_SEPARATOR + extension;
  }
}
