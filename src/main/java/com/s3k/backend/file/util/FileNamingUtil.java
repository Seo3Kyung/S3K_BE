package com.s3k.backend.file.util;

import org.springframework.stereotype.Component;

@Component
public class FileNamingUtil {

  private static final String SEP = "_";
  private static final String EXT_SEPARATOR = ".";

  public static String makePhotoFileName(final String snsId, final String randomUUID,
      final String extension) {
    return snsId + SEP + randomUUID + EXT_SEPARATOR + extension;
  }
}
