package com.s3k.backend.file.adapter;

import com.s3k.backend.file.interfaces.FileStorage;
import com.s3k.backend.file.util.FileNamingUtil;
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
public class S3StorageAdapter implements FileStorage {

  private final S3Template s3Template;
  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucketName;

  public S3StorageAdapter(S3Template s3Template) {
    this.s3Template = s3Template;
  }

  @Override
  public String save(MultipartFile file, String snsId) {
    String extension = FileNamingUtil.getExtension(file);
    String fileName = FileNamingUtil.makePhotoFileName(snsId, UUID.randomUUID().toString(), extension);
    try {
      upload(file, fileName);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
    return fileName;
  }

  private void upload(MultipartFile file, String key) throws IOException {
    ObjectMetadata objectMetadata = ObjectMetadata.builder().build();
    s3Template.upload(bucketName, key, file.getInputStream(), objectMetadata);
  }
}