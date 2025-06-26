package com.s3k.backend.file.service;

import com.s3k.backend.file.interfaces.FileStorage;
import com.s3k.backend.member.mapper.MemberMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
  private final MemberMapper memberMapper;
  private final FileStorage localStorage;
  private final FileStorage s3StorageAdapter;

  @Autowired
  public FileService(
      MemberMapper memberMapper,
      @Qualifier("local") FileStorage localStorage,
      @Qualifier("s3") FileStorage s3StorageAdapter
  ) {
    this.memberMapper = memberMapper;
    this.localStorage = localStorage;
    this.s3StorageAdapter = s3StorageAdapter;
  }

  @Transactional
  public String savePhotoForRegister(MultipartFile file, String snsId) throws IOException {
    String fileName = localStorage.savePhoto(file, snsId);
    memberMapper.updatePendingMemberProfile(snsId, fileName);
    return fileName;
  }

  public String savePhotoInS3(MultipartFile file, String snsId) throws IOException {
    String key = s3StorageAdapter.savePhoto(file, snsId);
    memberMapper.updatePendingMemberProfile(snsId, key);
    return key;
  }
}