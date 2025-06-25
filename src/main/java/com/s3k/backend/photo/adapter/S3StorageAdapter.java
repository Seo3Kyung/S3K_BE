package com.s3k.backend.photo.adapter;

import com.s3k.backend.photo.interfaces.PhotoStorage;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Qualifier("s3")
public class S3StorageAdapter implements PhotoStorage {

  private final S3Template s3Template;
  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucketName;

  public S3StorageAdapter(S3Template s3Template) {
    this.s3Template = s3Template;
  }

  @Override
  public String savePhoto(MultipartFile file, String snsId) throws IOException {
    String fileName = file.getOriginalFilename();
    String extension = getExtension(fileName);
    String key = snsId + UUID.randomUUID();
    validateMimeType(file.getContentType(), extension);
    upload(file, key);
    return key;
  }

  private String getExtension(String fileName) throws IOException {
    if(fileName == null) return null;
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  private void validateMimeType(String mimeType, String extension) {
    switch(extension) {
      case "jpeg": if(!mimeType.equals("image/jpeg")) { throw new IllegalArgumentException(); }
      case "jpg": if(!mimeType.equals("image/jpg")) { throw new IllegalArgumentException(); }
      case "png": if(!mimeType.equals("image/png")) { throw new IllegalArgumentException(); }
      default: throw new IllegalArgumentException();
    }
  }

  private void upload(MultipartFile file, String key) throws IOException {
    ObjectMetadata objectMetadata = ObjectMetadata.builder().build();
    s3Template.upload(bucketName, key, file.getInputStream(), objectMetadata);
  }
}