package com.s3k.backend.file.service;

import com.s3k.backend.file.adapter.FileData;
import com.s3k.backend.file.dto.FileDto;
import com.s3k.backend.file.interfaces.FileStorageService;
import com.s3k.backend.file.util.FileNamingUtil;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Qualifier("s3")
public class S3StorageService implements FileStorageService {

  private final S3Template s3Template;
  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucketName;

  public S3StorageService(S3Template s3Template) {
    this.s3Template = s3Template;
  }

//  @Override
//  public FileDto save(String baseDir, MultipartFile file, String snsId) {
//    String extension = FileNamingUtil.getExtension(file);
//    String fileName = FileNamingUtil.makePhotoFileName(snsId, UUID.randomUUID().toString(), extension);
//    try {
//      upload2(file, fileName);
//    } catch (IOException e) {
//      e.printStackTrace();
//      throw new RuntimeException(e.getMessage());
//    }
//    return null;
//  }
//
//  @Override
//  public byte[] getFile(String dirPath, String fileName) {
//    return new byte[0];
//  }

  @Override
  public FileDto upload(FileData fileData, String path) throws IOException {
    return null;
  }

  @Override
  public Optional<FileData> download(String path) throws IOException {
    return Optional.empty();
  }

  @Override
  public boolean delete(String path) throws IOException {
    return false;
  }

  @Override
  public boolean exists(String path) throws IOException {
    return false;
  }

  @Override
  public Optional<FileDto> getFileInfo(String path) throws IOException {
    return Optional.empty();
  }

  private void upload2(MultipartFile file, String key) throws IOException {
    ObjectMetadata objectMetadata = ObjectMetadata.builder().build();
    s3Template.upload(bucketName, key, file.getInputStream(), objectMetadata);
  }
}