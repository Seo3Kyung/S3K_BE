package com.s3k.backend.file.interfaces;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

  String savePhoto(MultipartFile file, String snsId) throws IOException;
}