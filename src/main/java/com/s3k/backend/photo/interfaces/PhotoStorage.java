package com.s3k.backend.photo.interfaces;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoStorage {

  String savePhoto(MultipartFile file, String snsId) throws IOException;
}