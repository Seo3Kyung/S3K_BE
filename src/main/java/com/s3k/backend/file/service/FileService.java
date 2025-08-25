package com.s3k.backend.file.service;

import com.s3k.backend.file.adapter.FileData;
import com.s3k.backend.file.dto.FileDto;
import com.s3k.backend.file.dto.query.UpdateFile;
import com.s3k.backend.file.entity.FileEntity;
import com.s3k.backend.file.interfaces.FileStorageService;
import com.s3k.backend.file.mapper.FileMapper;
import com.s3k.backend.file.util.FileNamingUtil;
import com.s3k.backend.global.util.DateTimeUtil;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.mapper.MemberMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileService {

  private final MemberMapper memberMapper;
  private final FileMapper fileMapper;
  private final FileStorageService localStorage;
  private final FileStorageService s3StorageAdapter;
  private final FileResizeService fileResizeService;


  @Autowired
  public FileService(
      MemberMapper memberMapper,
      FileMapper fileMapper,
      @Qualifier("local") FileStorageService localStorage,
      @Qualifier("s3") FileStorageService s3StorageAdapter,
      FileResizeService fileResizeService) {
    this.memberMapper = memberMapper;
    this.fileMapper = fileMapper;
    this.localStorage = localStorage;
    this.s3StorageAdapter = s3StorageAdapter;
    this.fileResizeService = fileResizeService;
  }

  @Transactional
  public void saveProfileForRegister(
      MultipartFile file, String snsId
  ) {
    String extension =  FileNamingUtil.getExtension(file);
    String filename = FileNamingUtil.makePhotoFileName(snsId, extension);
    try {
      byte[] resizedImageData = fileResizeService.resizeImageBasic(file, extension);
      FileData fileData = FileData.fromByteArray(resizedImageData, filename, file.getContentType());
      FileDto fileDto = localStorage.upload(fileData);
      fileMapper.saveFile(fileDto);
      memberMapper.updatePendingMemberProfile(snsId, fileDto.getFileId());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public String saveProfileForS3(
      String snsId
  ) {
    Member member = memberMapper.getMemberDetailBySnsId(snsId);
    FileEntity fileEntity = fileMapper.getFile(member.getProfileImageId());
    try {
      FileData fileData = localStorage.download(fileEntity.getFilePath(), fileEntity.getFileName())
          .orElseThrow(() -> new RuntimeException("프로필 파일이 존재하지 않습니다."));
      FileDto fileDto = s3StorageAdapter.upload(fileData);
      fileMapper.updateFile(
          UpdateFile.builder()
              .fileId(fileDto.getFileId())
              .filePath(fileDto.getFilePath())
              .fileStatus(fileDto.getFileStatus())
              .updateDatetime(fileDto.getUpdateDatetime())
              .build()
      );
      boolean isDeleted = localStorage.delete(fileEntity.getFilePath(), fileEntity.getFileName());
      if(!isDeleted){
        log.info("파일이 로컬에서 삭제되지 않았습니다.");
        System.out.println("파일이 로컬에서 삭제되지 않았습니다.");
      }
      return fileDto.getFilePath();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
//    byte[] file = localStorage.getFile(baseDir, fileEntity.getFileName());
//    s3StorageAdapter.save();
//    s3StorageAdapter.save();
//    Member member = memberMapper.getMemberDetailBySnsId(snsId);
//    fileMapper.getFile()
//    memberMapper.updatePendingMemberProfile(snsId, key);
  }

  @Transactional
  public void saveImagesForS3(
      List<Long> imageIds
  ) {
    List<FileEntity> files = fileMapper.getFiles(imageIds).orElse(Collections.emptyList());
    if(files.isEmpty()) return;

    List<FileData> fileDataList = files.stream()
        .map(file -> {
          try {
            return localStorage.download(file.getFilePath(), file.getFileName())
                .orElse(null);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }).filter(Objects::nonNull).toList();
    fileDataList.forEach(fileData -> {
      try {
        FileDto fileDto = s3StorageAdapter.upload(fileData);
        fileMapper.updateFile(
            UpdateFile.builder()
                .fileId(fileDto.getFileId())
                .filePath(fileDto.getFilePath())
                .fileStatus(fileDto.getFileStatus())
                .updateDatetime(fileDto.getUpdateDatetime())
                .build()
        );
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    files.forEach(file -> {
      boolean isDeleted = false;
      try {
        isDeleted = localStorage.delete(file.getFilePath(), file.getFileName());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      if(!isDeleted){
        log.info("파일이 로컬에서 삭제되지 않았습니다.");
        System.out.println("파일이 로컬에서 삭제되지 않았습니다.");
      }
    });
  }
}