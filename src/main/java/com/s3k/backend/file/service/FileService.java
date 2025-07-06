package com.s3k.backend.file.service;

import com.s3k.backend.file.adapter.FileData;
import com.s3k.backend.file.dto.FileDto;
import com.s3k.backend.file.entity.FileEntity;
import com.s3k.backend.file.interfaces.FileStorageService;
import com.s3k.backend.file.mapper.FileMapper;
import com.s3k.backend.file.util.FileNamingUtil;
import com.s3k.backend.global.util.DateTimeUtil;
import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
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
      FileDto fileDto = localStorage.upload(fileData, DateTimeUtil.ofNowToStringDate("yyyy-MM-dd"));
      fileMapper.saveFile(fileDto);
      memberMapper.updatePendingMemberProfile(snsId, fileDto.getFileId());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public String saveProfileForS3(
      String snsId
  ) {
    Member member = memberMapper.getMemberDetailBySnsId(snsId);
    FileEntity fileEntity = fileMapper.getFile(member.getProfileImageId());
//    byte[] file = localStorage.getFile(baseDir, fileEntity.getFileName());
//    s3StorageAdapter.save();
//    s3StorageAdapter.save();
//    Member member = memberMapper.getMemberDetailBySnsId(snsId);
//    fileMapper.getFile()
//    memberMapper.updatePendingMemberProfile(snsId, key);
    return "";
  }
}