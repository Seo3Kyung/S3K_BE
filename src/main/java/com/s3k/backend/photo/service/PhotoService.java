package com.s3k.backend.photo.service;

import com.s3k.backend.member.mapper.MemberMapper;
import com.s3k.backend.photo.interfaces.PhotoStorage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {
  private final MemberMapper memberMapper;
  private final PhotoStorage localPhotostorage;
  private final PhotoStorage s3StorageAdapter;

  @Autowired
  public PhotoService(
      MemberMapper memberMapper,
      @Qualifier("local") PhotoStorage localPhotostorage,
      @Qualifier("s3") PhotoStorage s3StorageAdapter
  ) {
    this.memberMapper = memberMapper;
    this.localPhotostorage = localPhotostorage;
    this.s3StorageAdapter = s3StorageAdapter;
  }

  @Transactional
  public String savePhotoForRegister(MultipartFile file, String snsId) throws IOException {
    String fileName = localPhotostorage.savePhoto(file, snsId);
    memberMapper.updatePendingMemberProfile(snsId, fileName);
    return fileName;
  }

  public String savePhotoInS3(MultipartFile file, String snsId) throws IOException {
    String key = s3StorageAdapter.savePhoto(file, snsId);
    memberMapper.updatePendingMemberProfile(snsId, key);
    return key;
  }
}