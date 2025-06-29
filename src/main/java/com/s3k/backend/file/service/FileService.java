package com.s3k.backend.file.service;

import com.s3k.backend.file.dto.FileDto;
import com.s3k.backend.file.interfaces.FileStorage;
import com.s3k.backend.file.mapper.FileMapper;
import com.s3k.backend.member.entity.Member;
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
  private final FileMapper fileMapper;
  private final FileStorage localStorage;
  private final FileStorage s3StorageAdapter;

  @Autowired
  public FileService(
      MemberMapper memberMapper,
      FileMapper fileMapper,
      @Qualifier("local") FileStorage localStorage,
      @Qualifier("s3") FileStorage s3StorageAdapter
  ) {
    this.memberMapper = memberMapper;
    this.fileMapper = fileMapper;
    this.localStorage = localStorage;
    this.s3StorageAdapter = s3StorageAdapter;
  }

  @Transactional
  public String saveProfileForRegister(MultipartFile file, String snsId) {
    FileDto fileDto = localStorage.save(file, snsId);
    fileMapper.saveFile(fileDto);
    memberMapper.updatePendingMemberProfile(snsId, fileDto.getFileId());
    return "파일이 정상적으로 임시 저장되었습니다.";
  }

  public String saveProfileS3(MultipartFile file, String snsId) {
//    Member member = memberMapper.getMemberDetailBySnsId(snsId);
//    fileMapper.getFile()
//    memberMapper.updatePendingMemberProfile(snsId, key);
    return "";
  }
}