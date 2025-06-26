package com.s3k.backend.global.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface Validator {
  void validate(MultipartFile file);
}
