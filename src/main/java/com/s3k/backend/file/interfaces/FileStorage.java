package com.s3k.backend.file.interfaces;

import com.s3k.backend.file.dto.FileDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {

  FileDto save(MultipartFile file, String snsId);
}