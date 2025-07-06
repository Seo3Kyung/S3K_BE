package com.s3k.backend.file.service;

import com.s3k.backend.file.adapter.FileData;
import com.s3k.backend.file.dto.FileDto;
import com.s3k.backend.file.enums.FileStatus;
import com.s3k.backend.file.enums.FileType;
import com.s3k.backend.file.interfaces.FileStorageService;
import com.s3k.backend.file.util.FileNamingUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Qualifier("local")
@Slf4j
public class LocalStorageService implements FileStorageService {

  @Value("${app.storage.local.base-dir}")
  private String basePath;

//  @Override
//  public FileDto save(String dirPath, MultipartFile file, String snsId) {
//    String extension = FileNamingUtil.getExtension(file);
//    String randomUUID = UUID.randomUUID().toString();
//    String filename = FileNamingUtil.makePhotoFileName(snsId, randomUUID, extension);
//
//    try {
//      Path directory = Paths.get(dirPath);
//      if (Files.notExists(directory)) {
//        Files.createDirectories(directory);
//      }
//      InputStream inputStream = file.getInputStream();
//      Files.copy(inputStream, directory.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
//      return FileDto.of(file, FileType.PROFILE.getValue(), filename, dirPath);
//    } catch (IOException e) {
//      log.error(e.getMessage());
//      throw new RuntimeException(e);
//    }
//  }

//  public byte[] getFile(String dirPath, String fileName) {
//    Path directory = Paths.get(dirPath);
//    if(Files.notExists(directory)) { return null; }
//
//    try(Stream<Path> paths = Files.list(directory)) {
//      Path filePath = paths.filter(path -> path.getFileName().toString().equals(fileName))
//          .findFirst()
//          .orElse(null);
//      if(filePath == null) { return null; }
//      return Files.readAllBytes(filePath);
//    } catch (IOException e) {
//      log.error(e.getMessage());
//      throw new RuntimeException(e);
//    }
//  }

  @Override
  public FileDto upload(FileData fileData, String path) throws IOException {
    Path targetPath = Paths.get(basePath, path);

    Files.createDirectories(targetPath);

    try (InputStream inputStream = fileData.getInputStream()) {
      Path targetFilePath = targetPath.resolve(fileData.getOriginalFilename());
      Files.copy(inputStream, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
      log.info("파일 저장 완료: {}", targetFilePath);
    }

    return FileDto.of(
        fileData,
        FileType.PROFILE.getValue(),
        targetPath.toString(),
        FileStatus.TEMP.getValue()
    );
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

  public void deleteExistingFile(String dirPath, String fileName) {
    Path directory = Paths.get(dirPath);
    if(Files.notExists(directory)) { return; }

    try(Stream<Path> paths = Files.list(directory)){
      paths.filter(path -> path.getFileName().toString().equals(fileName))
          .forEach(path -> {
            try {
              Files.delete(path);
            } catch (IOException e) {
              log.error(e.getMessage());
            }
          });
    }catch(IOException e){
      log.error(e.getMessage());
    }
  }

  public List<String> deleteExistingFileParallel(String dirPath, String snsId) {
    Path directory = Paths.get(dirPath);

    if (!Files.exists(directory)) {
      return Collections.emptyList();
    }

    List<String> deletedFiles = new ArrayList<>();
    List<String> failedFiles = new ArrayList<>();

    try (Stream<Path> paths = Files.list(directory)) {
      paths.filter(path -> path.getFileName().toString().startsWith(snsId))
          .parallel() // 병렬 처리
          .forEach(path -> {
            try {
              Files.delete(path);
              synchronized (deletedFiles) {
                deletedFiles.add(path.toString());
              }
            } catch (IOException e) {
              synchronized (failedFiles) {
                failedFiles.add(path.toString());
              }
            }
          });
    } catch (IOException e) {
      throw new RuntimeException("파일 목록 조회 중 오류가 발생했습니다.", e);
    }

    log.info("삭제 완료: {} 개, 실패: {} 개", deletedFiles.size(), failedFiles.size());
    return deletedFiles;
  }
}