package com.s3k.backend.file.controller;

import com.s3k.backend.file.service.FileService;
import com.s3k.backend.file.validator.ImageValidator;
import com.s3k.backend.global.dto.ApisResponse;
import com.s3k.backend.member.entity.Member;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;
  private final ImageValidator imageValidator;

  @PostMapping("/register")
  public ApisResponse<String> saveProfileImageForRegister(
      @RequestParam("file") MultipartFile file,
      @AuthenticationPrincipal Member principal) {
    imageValidator.validate(file);
    return ApisResponse.ok(fileService.saveProfileForRegister(file, principal.getSnsId()));
  }

  @PostMapping("/s3/save")
  public ApisResponse<String> saveProfileImageForSave(
      @RequestPart MultipartFile file,
      @AuthenticationPrincipal Member principal) throws IOException {
    return ApisResponse.ok(fileService.savePhotoInS3(file, principal.getSnsId()));
  }
}